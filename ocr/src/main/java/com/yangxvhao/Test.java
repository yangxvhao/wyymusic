package com.yangxvhao;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * @author yangxvhao
 * @date 17-12-29.
 */

public class Test {
    public static void main(String[] args) {
        File file = new File("/home/yangxvhao/yangxh/picture/ygrandimg.jpg");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/home/yangxvhao/tessdata");
        try {
            String result = tesseract.doOCR(file);
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
