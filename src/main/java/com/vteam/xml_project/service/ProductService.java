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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
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
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

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
    public ProductListDTO getProductList() {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProduct = productDAO.getProductList();
            ProductDTO p ;
            for (Product d : dbProduct) {
                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setDescription(d.getDescription());
                p.setCategoryName(d.getCategory().getCategoryName());
                list.getProductList().add(p);
            }
            list.setNumberOfProduct(productDAO.getNumberOfProduct());
            list.setStatus("success");
        }catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        System.out.println(list);
        return list;
    }
    
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
            log.error(jaxbEx);
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

    private ProductListDTO convertFromNodeToProductListDTO(Node node) throws JAXBException {
        ProductListDTO result = XMLUtil.UnMarshall(ProductListDTO.class, node);
        return result;
    }

    private ProductListDTO findProductListDTOInCategoryXML(String realPath, int categoryId) throws XMLStreamException, JAXBException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        StreamSource xml = new StreamSource(realPath + "category.xml");
        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
        //find tag category
        xsr.nextTag();
        boolean found = false;
        while (xsr.hasNext()) {
            if (xsr.getEventType() == XMLStreamReader.START_ELEMENT) {
                if (xsr.getLocalName().equals("category")) {
                    xsr.nextTag();
                    if (xsr.getLocalName().equals("id")) {
                        xsr.next();
                        int id = Integer.parseInt(xsr.getText());
                        if (id == categoryId) {
                            xsr.next();
                            while(xsr.hasNext()){
                                if(xsr.getEventType() == XMLStreamReader.START_ELEMENT){
                                    if(xsr.getLocalName().equals("productList")){
                                        found = true;
                                        break;
                                    }
                                }
                                xsr.next();
                            }
                        }
                    }
                }
            }
            if(found){
                break;
            }
            xsr.next();
        }
        if (found) {
            return XMLUtil.UnMarshall(ProductListDTO.class, xsr);
        }
        return null;
    }

    @Transactional
    public ProductListDTO searchProductByCategoryId(int category_id, int page, boolean isInit) {
        ProductListDTO list = new ProductListDTO();
        try {
            if (page == 1 && !isInit) {
                String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/") + "/";
                list = findProductListDTOInCategoryXML(realPath, category_id);
                return list;
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
        } catch (JAXBException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors ! Try again");
        } catch (XMLStreamException ex) {
            java.util.logging.Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
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
