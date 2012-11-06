package com.spshop.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Product;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.ProductService;

public class FeedTools {
    private static final String excel_path = "/home/project/fee/products.xls";
    
    private static final String CONDITION_NEW = "new";
    private static final String CONDITION_USED = "used";
    private static final String CONDITION_REFURBISHED = "refurbished";
    
//    'in stock'
//    'available for order'
//    'out of stock'
//    'preorder'
    private static final String AVAILABILITY_IN_STOCK = "in stock";
    private static final String AVAILABILITY_AVAILABLE = "available for order";
    private static final String AVAILABILITY_OUT_OF_STOCK = "out of stock";
    private static final String AVAILABILITY_PREORDER = "preorder";
    
    
    public static void generateProductsExcel(){
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(excel_path));
            WritableSheet sheet = workbook.createSheet("products", 0);
            List<Product> products = ServiceFactory.getService(ProductService.class).loadAllProduct(5000);
            
            Label tlabel1 = new Label(0, 0, "id");
            Label tlabel2 = new Label(1, 0, "title");
            Label tlabel3 = new Label(2, 0, "description");
            Label tlabel4 = new Label(3, 0, "product type");
            Label tlabel5 = new Label(4, 0, "link");
            Label tlabel6 = new Label(5, 0, "image link");
            Label tlabel7 = new Label(6, 0, "condition");
            Label tlabel8 = new Label(7, 0, "availability");
            Label tlabel9 = new Label(8, 0, "price");
            Label tlabel10 = new Label(9, 0, "sale price");
            sheet.addCell(tlabel1);
            sheet.addCell(tlabel2);
            sheet.addCell(tlabel3);
            sheet.addCell(tlabel4);
            sheet.addCell(tlabel5);
            sheet.addCell(tlabel6);
            sheet.addCell(tlabel7);
            sheet.addCell(tlabel8);
            sheet.addCell(tlabel9);
            sheet.addCell(tlabel10);
            
            for (int i = 0; i < products.size(); i++) {
                Label label1 = new Label(0, i+1, validateIfNull(String.valueOf(products.get(i).getId()))+"-us");
                Label label2 = new Label(1, i+1, validateIfNull(products.get(i).getTitle()));
                Label label3 = new Label(2, i+1, validateIfNull(products.get(i).getKeywords()));
                Label label4 = new Label(3, i+1, products.get(i).getCategories().get(0).getDisplayName());
                Label label5 = new Label(4, i+1, validateIfNull(SCacheFacade.getSite().getDomain()+ Constants.URL_SEPERATOR +products.get(i).getName()));
                Label label6 = new Label(5, i+1, validateIfNull(SCacheFacade.getSite().getDomain()+ Constants.URL_SEPERATOR +products.get(i).getImages().get(0).getThumbnailUrl()));
                Label label7 = new Label(6, i+1, CONDITION_NEW);
                Label label8 = new Label(7, i+1, AVAILABILITY_AVAILABLE);
                Label label9 = new Label(8, i+1, validateIfNull(String.valueOf(products.get(i).getPrice())));
                Label label10 = new Label(9, i+1, validateIfNull(String.valueOf(products.get(i).getActualPrice())));
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
                sheet.addCell(label4);
                sheet.addCell(label5);
                sheet.addCell(label6);
                sheet.addCell(label7);
                sheet.addCell(label8);
                sheet.addCell(label9);
                sheet.addCell(label10);
                
            }

            workbook.write(); 
            workbook.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
    
    private static String validateIfNull(String value){
        if (value == null) {
            return "";
        }
        return value;
    }
}
