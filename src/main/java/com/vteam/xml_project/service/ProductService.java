/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.SearchCacheDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.hibernate.orm.SearchCache;
import com.vteam.xml_project.util.XMLUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class ProductService {

    private static Logger log = Logger.getLogger(ProductService.class.getName());
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private BidDAO bidDAO;
    @Autowired
    private SearchCacheDAO searchCacheDAO;
    @Autowired
    ServletContext servletContext;
    private static final int PAGESIZE = 50;
    private static final int CACHETIMEOUT = 24;

    @Transactional
    public ProductListDTO getProductList(int page) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.getProductList(page, PAGESIZE);
            ProductDTO p;
            Bids bid;
            long time = 0;
            Date currentDate = new Date();
            for (Product d : dbProducts) {
                if (d.getBidId() != null) {
                    bid = bidDAO.getBidById(d.getBidId());
                    time = bid.getStartDate().getTime() - currentDate.getTime();
                }
                p = new ProductDTO();
                p.setBidTimeRemain(time);
                p.setName(d.getProductName());
                p.setDescription(d.getDescription());
                p.setImage("/resources/img/product/" + d.getImage());
                p.setImageName(d.getImage());
                p.setId(d.getId());
                p.setBidId(d.getBidId());
                list.getProductList().add(p);
            }
            list.setNumberOfProduct(productDAO.getNumberOfProduct());
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;

    }

    private boolean checkForCache(SearchCache searchCache) {

        return (searchCache != null && (searchCache.getCacheDate().getHours() - new Date().getHours()) < CACHETIMEOUT);
    }

    private ProductListDTO convertCacheToProductListDTO(String filePath) throws JAXBException {
        return XMLUtil.UnMarshall(ProductListDTO.class, filePath);
    }

    private void converProductListDTOToCache(ProductListDTO productList, String filePath) throws JAXBException {
        XMLUtil.Marshall(productList, filePath);
    }

    @Transactional
    public ProductListDTO searchProduct(String txtSearch, int page) {
        String appPath = servletContext.getRealPath("WEB-INF/cache/");
        appPath = appPath + "/";
        String fileName = txtSearch.trim() + "_" + page + "_search_product_cache.xml";
        ProductListDTO list = new ProductListDTO();
        try {
            SearchCache searchCache = searchCacheDAO.getSearchCacheByQuery(txtSearch + "_" + page);
            if (checkForCache(searchCache)) {
                list = convertCacheToProductListDTO(appPath + searchCache.getFileName());
                list.setStatus("success");
                return list;
            } else {
                List<Product> dbProducts = productDAO.searchProduct(txtSearch, page, PAGESIZE);
                ProductDTO p;

                List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
                for (Product d : dbProducts) {
                    p = new ProductDTO();
                    p.setName(d.getProductName());
                    p.setId(d.getId());
                    p.setDescription(d.getDescription());
                    p.setImage("/resources/img/product/" + d.getImage());
                    p.setImageName(d.getImage());
                    p.setCategoryName(d.getCategory().getCategoryName());
                    p.setBidId(d.getBidId());
                    tmpList.add(p);
                }
                list.setProductList(tmpList);
                converProductListDTOToCache(list, appPath + fileName);
                list.setStatus("success");

                if (searchCache == null) {
                    searchCache = new SearchCache();
                }
                searchCache.setCacheDate(new Date());
                searchCache.setFileName(fileName);
                searchCache.setQuery(txtSearch + "_" + page);
                searchCacheDAO.save(searchCache);
            }
        } catch (HibernateException ex) {
            log.error(ex.getMessage());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        } catch (JAXBException jaxbEx) {
            log.error(jaxbEx.getMessage());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }

    @Transactional
    public ProductDTO getProductById(int id) {
        ProductDTO productDTO = new ProductDTO();
        Bids bid;
        long time = 0;
        Date currentDate = new Date();
        try {
            Product product = productDAO.getProductById(id);
            if (product.getBidId() != null) {
                bid = bidDAO.getBidById(product.getBidId());
                time = bid.getStartDate().getTime() - currentDate.getTime();
            }
            productDTO.setBidTimeRemain(time);
            productDTO.setId(product.getId());
            productDTO.setName(product.getProductName());
            productDTO.setCategoryName(product.getCategory().getCategoryName());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setDescription(product.getDescription());
            productDTO.setMinPrice(product.getMinPrice());
            productDTO.setMaxPrice(product.getMaxPrice());
            productDTO.setImage("/resources/img/product/" + product.getImage());
            productDTO.setImageName(product.getImage());
            productDTO.setBidId(product.getBidId());
            if (product.getBidId() != null) {
                Bids bids = bidDAO.getBidById(product.getBidId());
                if (bids != null) {
                    productDTO.setBidCost(bids.getCost());
                }
            }
            productDTO.setStatus("success");

        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            productDTO.setStatus("error");
            productDTO.setMsg("Have some errors. Try again");
        }
        return productDTO;
    }

    private ProductListDTO convertFromNodeToProductListDTO(Node node) throws JAXBException{
        ProductListDTO result = XMLUtil.UnMarshall(ProductListDTO.class, node);
        return result;
    }
    @Transactional
    public ProductListDTO searchProductByCategoryId(int category_id, int page) {
        ProductListDTO list = new ProductListDTO();
        try {
            if (page == 1) {
                String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/") + "/";
                Document doc = XMLUtil.parseDOM(realPath + "category.xml");
                XPathFactory xpf = XPathFactory.newInstance();
                XPath xpath = xpf.newXPath();
                String exp ="//category[id= "+category_id+"]/productList";
                Node productListNode= (Node) xpath.evaluate(exp, doc, XPathConstants.NODE);
                return convertFromNodeToProductListDTO(productListNode);
            }
            List<Product> dbProducts = productDAO.searchProductByCategoryId(category_id, page, PAGESIZE);
            ProductDTO pd;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {
                pd = new ProductDTO();
                pd.setName(d.getProductName());
                pd.setId(d.getId());
                pd.setDescription(d.getDescription());
                pd.setImage("/resources/img/product/" + d.getImage());
                pd.setMinPrice(d.getMinPrice());
                pd.setMaxPrice(d.getMaxPrice());
                pd.setBidId(d.getBidId());
                tmpList.add(pd);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        }catch (IOException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors ! Try again");
        } catch (SAXException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors ! Try again");
        } catch (XPathExpressionException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors ! Try again");
        } catch (JAXBException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors ! Try again");
        } catch (ParserConfigurationException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors ! Try again");
        }
        return list;
    }

    @Transactional
    public ProductListDTO searchProductByTagsId(int tags_id, int page) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.searchProductByTagID(tags_id, page, PAGESIZE);
            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            ProductDTO pd;
            for (Product d : dbProducts) {
                pd = new ProductDTO();
                pd.setName(d.getProductName());
                pd.setId(d.getId());
                pd.setDescription(d.getDescription());
                pd.setImage("/resources/img/product/" + d.getImage());
                pd.setBidId(d.getBidId());
                tmpList.add(pd);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors . Try again");
        }
        return list;
    }

    @Transactional
    public ProductListDTO getProductNameList(int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.getProductNameList(page, pageSize);
            ProductDTO p;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {

                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setId(d.getId());
                p.setBidId(d.getBidId());

                tmpList.add(p);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getMessage());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }

    @Transactional
    public ProductListDTO getAllProductNameList(int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.getAllProductNameList(page, pageSize);
            ProductDTO p;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {

                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setId(d.getId());
                p.setBidId(d.getBidId());

                tmpList.add(p);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getMessage());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }
}
