/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.NinCodeDTO;
import com.vteam.xml_project.dto.NinCodeListDTO;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.dto.TagsDTO;
import com.vteam.xml_project.dto.UploadResultDTO;
import com.vteam.xml_project.dto.UserListDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.CardCodeDAO;
import com.vteam.xml_project.hibernate.dao.CategoryDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.TagsDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.CardCode;
import com.vteam.xml_project.hibernate.orm.Category;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.hibernate.orm.Tags;
import com.vteam.xml_project.util.DateUtil;
import com.vteam.xml_project.util.StringUtil;
import com.vteam.xml_project.util.XMLUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.fop.apps.FOPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phitt60230
 */
@Service
public class AdminService {

    private static final long MAX_UPLOAD_BYTES = 5242880;
    private static final String SERVERIMGPATH = "/resources/img/product/";
    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private BidDAO bidDAO;
    @Autowired
    private CardCodeDAO cardCodeDAO;
    @Autowired
    private TagsDAO tagsDAO;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private DateUtil dateUtil;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    BidService bidService;
    private static String CATEGORY_XML_FILE_NAME = "category.xml";
    private static String USER_XML_FILE_NAME = "user.xml";
    private static String BID_XML_FILE_NAME = "bids.xml";

    @Transactional
    public ProductDTO insertProduct(int categoryId, String productName, String description, String img, double minPrice, double maxPrice, String tags) {
        ProductDTO productDTO = new ProductDTO();
        try {
            Category category = categoryDAO.getCategoryById(categoryId);
            Product newProduct = new Product(category, productName, description, img, Product.Status.AVAILABLE, minPrice, maxPrice, true);
            String[] tagsList = StringUtil.splitString(tags);
            Tags tag;
            for (String tagStr : tagsList) {
                tag = tagsDAO.getTagById(Integer.parseInt(tagStr));
                if (tag != null) {
                    newProduct.getTagses().add(tag);
                }
            }
            productDAO.save(newProduct);
            productDTO.setName(newProduct.getProductName());
            productDTO.setDescription(newProduct.getDescription());
            productDTO.setImage(newProduct.getImage());
            productDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            productDTO.setStatus("error");
            productDTO.setMsg("Have some errors . Try again");
        }
        return productDTO;

    }

    @Transactional
    public ProductDTO updateProduct(int productId, int categoryId, String productName, String description, String img, double minPrice, double maxPrice, String tags) {
        ProductDTO productDTO = new ProductDTO();
        try {
            Product product = productDAO.getProductById(productId);
            Category category = categoryDAO.getCategoryById(categoryId);
            product.setCategory(category);
            product.setProductName(productName);
            product.setDescription(description);
            product.setImage(img);
            product.setMinPrice(minPrice);
            product.setMaxPrice(maxPrice);
            String[] tagsList = StringUtil.splitString(tags);
            Tags tag;
            for (String tagStr : tagsList) {
                tag = tagsDAO.getTagById(Integer.parseInt(tagStr));
                if (tag != null) {
                    product.getTagses().add(tag);
                }
            }
            productDAO.save(product);
            productDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            productDTO.setStatus("error");
            productDTO.setMsg("Have some errors . Try again");
        }
        return productDTO;
    }

    @Transactional
    public ProductListDTO getProductList(int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.getProductListInorgeStatus(page, pageSize);
            ProductDTO p;
            for (Product d : dbProducts) {

                p = new ProductDTO();
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

    @Transactional
    public ProductDTO getProductDetail(int id) {
        Product dbProduct = productDAO.getProductById(id);
        ProductDTO result = new ProductDTO();
        if (dbProduct != null) {
            result.setId(dbProduct.getId());
            result.setName(dbProduct.getProductName());
            result.setCategoryId(dbProduct.getCategory().getId());
            result.setCategoryName(dbProduct.getCategory().getCategoryName());
            result.setDescription(dbProduct.getDescription());
            result.setMaxPrice(dbProduct.getMaxPrice());
            result.setMinPrice(dbProduct.getMinPrice());
            result.setImageName(dbProduct.getImage());
            result.setImage(SERVERIMGPATH+dbProduct.getImage());
            result.setStatus("success");
        } else {
            result.setStatus("error");
        }
        return result;
    }

    @Transactional
    public BidDTO insertBid(int product_id, String startDateStr, String endDateStr, int cost) {
        BidDTO bidDTO = new BidDTO();
        try {
            Date startDate = dateUtil.parseFromString(startDateStr, "yyyy/MM/dd HH:mm");
            Date endDate = dateUtil.parseFromString(endDateStr, "yyyy/MM/dd HH:mm");
            Product product = productDAO.getProductById(product_id);
            Bids newBid = new Bids(product, startDate, endDate, Bids.Status.UNCOMPLETED, cost);
            newBid.setLastEdit(new Date());
            bidDAO.save(newBid);

            bidDTO.setId(newBid.getId());
            bidDTO.setLast_edit(newBid.getLastEdit());
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setCost(cost);
            product.setBidId(newBid.getId());
            product.setStatus(Product.Status.ONBID);
            productDAO.save(product);
            bidDTO.setStatus("success");
            bidDTO.setIsCompleted(0);
            updateAllXML();

            return bidDTO;
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Have some errors. Try again");
        } catch (ParseException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Wrong date time format");
        }
        return bidDTO;
    }

    @Transactional
    public BidDTO updateBid(int bid_id, int product_id, String startDateStr, String endDateStr, String status, int cost) {
        BidDTO bidDTO = new BidDTO();
        try {
            Date startDate = dateUtil.parseFromString(startDateStr, "yyyy/MM/dd HH:mm");
            Date endDate = dateUtil.parseFromString(endDateStr, "yyyy/MM/dd HH:mm");
            Product product = productDAO.getProductById(product_id);
            Bids currentBid = bidDAO.getBidById(bid_id);
            Product currentProduct = currentBid.getProduct();
            currentProduct.setBidId(null);
            currentProduct.setStatus(Product.Status.AVAILABLE);
            productDAO.save(currentProduct);
            currentBid.setCost(cost);
            currentBid.setProduct(product);
            currentBid.setStartDate(startDate);
            currentBid.setEndDate(endDate);
            currentBid.setLastEdit(new Date());
            currentBid.setStatus(Bids.Status.valueOf(status));
            if (currentBid.getStatus().toString().equalsIgnoreCase("completed")) {
                product.setStatus(Product.Status.AVAILABLE);
                product.setBidId(null);
                bidDTO.setIsCompleted(1);
            }
            product.setBidId(currentBid.getId());
            product.setStatus(Product.Status.ONBID);
            productDAO.save(product);
            bidDAO.save(currentBid);
            bidDTO.setLast_edit(currentBid.getLastEdit());
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setCost(cost);
            bidDTO.setStatus("success");
            updateAllXML();
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Have some errors. Try again");
        } catch (ParseException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Wrong date time format");
        }
        return bidDTO;
    }

    @Transactional
    public CategoryDTO insertCategory(String categoryName, String description) {
        CategoryDTO categoryDTO = new CategoryDTO();
        try {
            Category category = new Category(categoryName, description);
            categoryDAO.save(category);
            categoryDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            categoryDTO.setStatus("error");
            categoryDTO.setMsg("Have some errors . Try again");
        }
        return categoryDTO;
    }

    @Transactional
    public CategoryDTO updateCategory(int categoryID, String description) {
        CategoryDTO categoryDTO = new CategoryDTO();
        try {
            Category category = categoryDAO.getCategoryById(categoryID);
            category.setDescription(description);
            categoryDAO.save(category);
            categoryDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            categoryDTO.setStatus("error");
            categoryDTO.setMsg("Have some errors. Try again");
        }
        return categoryDTO;
    }

    @Transactional
    public NinCodeListDTO generateNin(int amount, int quantity) {
        NinCodeListDTO ninCodeList = new NinCodeListDTO();
        try {
            CardCode cardCode;
            NinCodeDTO ninCodeDTO;
            for (int i = 0; i < quantity; i++) {
                cardCode = new CardCode(StringUtil.generateNin(), amount);
                cardCodeDAO.save(cardCode);
                ninCodeDTO = new NinCodeDTO();
                ninCodeDTO.setAmount(amount);
                ninCodeDTO.setCode(cardCode.getCode());
                ninCodeList.getNinList().add(ninCodeDTO);
            }
            ninCodeList.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            ninCodeList.setStatus("error");
            ninCodeList.setMsg("Have some errors. Try again");
        }
        return ninCodeList;
    }

    @Transactional
    public TagsDTO insertTag(String tagName, String description) {
        TagsDTO tagDTO = new TagsDTO();
        try {
            Tags tag = new Tags(tagName, description);
            tagsDAO.save(tag);
            tagDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            tagDTO.setStatus("error");
            tagDTO.setMsg("Have some errors . Try again");
        }
        return tagDTO;
    }

    @Transactional
    public TagsDTO updateTag(int tagID, String description) {
        TagsDTO tagDTO = new TagsDTO();
        try {
            Tags tag = tagsDAO.getTagById(tagID);
            tag.setDescription(description);
            tagsDAO.save(tag);
            tagDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            tagDTO.setStatus("error");
            tagDTO.setMsg("Have some errors. Try again");
        }
        return tagDTO;
    }

    @Transactional
    public void updateAllXML() {
        marshallCategory();
        marshallUser();
        marshallBids();
    }

    private void marshallCategory() {
        try {
            CategoryListDTO categoryListDTO = categoryService.getCategoryList();
            for (CategoryDTO categoryDTO : categoryListDTO.getCategoryList()) {
                categoryDTO.setProductListDTO(productService.searchProductByCategoryId(categoryDTO.getId(), 1, true));
            }
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(categoryListDTO, realPath + "/" + CATEGORY_XML_FILE_NAME);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    private void marshallUser() {
        try {
            UserListDTO userListDTO = userService.getUserList();
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(userListDTO, realPath + "/" + USER_XML_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void marshallBids() {
        try {
            BidListDTO bidListDTO = bidService.getBidsList(1, 999);
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(bidListDTO, realPath + "/" + BID_XML_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
    

    @Transactional
    public ByteArrayOutputStream exportProductListToPdf() {
        try {
            List<Product> product = productDAO.getProductList();
            ProductListDTO productListDTO = new ProductListDTO();
            ProductDTO productDTO;
            for (Product p : product) {
                productDTO = new ProductDTO();
                productDTO.setName(p.getProductName());
                productDTO.setDescription(p.getDescription());
                productDTO.setCategoryName(p.getCategory().getCategoryName());
                productListDTO.getProductList().add(productDTO);
            }
            File xmlFile = File.createTempFile(UUID.randomUUID().toString(), "_product.xml");
            XMLUtil.Marshall(productListDTO, xmlFile.getAbsolutePath());
            String appPath = servletContext.getRealPath("WEB-INF/views/resources/xsl");
            return XMLUtil.printPDF(xmlFile.getAbsolutePath(), appPath + File.separator + "product.xsl");
        } catch (IOException ex) {
            log.error(ex);
        } catch (JAXBException ex) {
            log.error(ex);
        } catch (TransformerConfigurationException ex) {
            log.error(ex);
        } catch (TransformerException ex) {
            log.error(ex);
        } catch (FOPException ex) {
            java.util.logging.Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Transactional
    public ByteArrayOutputStream exportNinCodeToPdf() {
        try {
            List<CardCode> cardCodeList = cardCodeDAO.getCardCodeToPrint();
            NinCodeListDTO ninCodeListDTO = new NinCodeListDTO();
            NinCodeDTO ninCodeDTO;
            for (CardCode cardCode : cardCodeList) {
                ninCodeDTO = new NinCodeDTO();
                ninCodeDTO.setCode(cardCode.getCode());
                ninCodeDTO.setAmount(cardCode.getAmount());
                ninCodeListDTO.getNinList().add(ninCodeDTO);
            }
            File xmlFile = File.createTempFile(UUID.randomUUID().toString(), "_nin.xml");
            xmlFile.deleteOnExit();
            XMLUtil.Marshall(ninCodeListDTO, xmlFile.getAbsolutePath());
            String appPath = servletContext.getRealPath("WEB-INF/views/resources/xsl");

            return XMLUtil.printPDF(xmlFile.getAbsolutePath(), appPath + File.separator + "nin_code_pdf.xsl");
        } catch (IOException ex) {
            log.error(ex);
        } catch (JAXBException ex) {
            log.error(ex);
        } catch (TransformerConfigurationException ex) {
            log.error(ex);
        } catch (TransformerException ex) {
            log.error(ex);
        } catch (FOPException ex) {
            java.util.logging.Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Transactional
    public ProductDTO deleteProduct(int productId) {
        ProductDTO productDTO = new ProductDTO();
        try {
            Product dbProduct = productDAO.getProductById(productId);
            dbProduct.setStatus(Product.Status.DELETED);
            productDAO.save(dbProduct);
            productDTO.setStatus("success");
        } catch (HibernateException ex) {
            ex.printStackTrace();
            productDTO.setStatus("error");
        }
        return productDTO;
    }

    public UploadResultDTO uploadToServer(FileItem uploadedFileItem) {
        UploadResultDTO result = new UploadResultDTO();
        try {
            if (uploadedFileItem == null
                    || uploadedFileItem.getName().length() == 0) {
                result.setStatus("error");
                result.setMsg("File empty");

            } else if (uploadedFileItem.getSize() > MAX_UPLOAD_BYTES) {
                result.setStatus("error");
                result.setMsg("File size is more than 5MB");
            } else if (!uploadedFileItem.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) && !uploadedFileItem.getContentType().equals(MediaType.IMAGE_PNG_VALUE)) {
                result.setStatus("error");
                result.setMsg("File is not image file (Only support JPEG and PNG)");
            } else {
                    String filePath = servletContext.getRealPath("WEB-INF/views/resources/img/product/") + File.separator+uploadedFileItem.getName();
                    File file = new File(filePath);
                    //file.createNewFile();
                    uploadedFileItem.write(file);
                    result.setImgPath(SERVERIMGPATH+uploadedFileItem.getName());
                    result.setImgName(uploadedFileItem.getName());
                    result.setStatus("success");
            }
        } catch (FileUploadException e) {
            result.setStatus("error");
            result.setMsg("Have some error. Try again");
            e.printStackTrace();
        } catch (Exception e) {
            result.setStatus("error");
            result.setMsg("Have some error. Try again");
            e.printStackTrace();
        }
        return result;
    }
}
