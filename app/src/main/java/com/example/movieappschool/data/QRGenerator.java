package com.example.movieappschool.data;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRGenerator {
    public void createQRCode() {
        QRGEncoder qrgEncoder = new QRGEncoder("Dit is een test", null, QRGContents.Type.TEXT, 500);

        Bitmap qrBits = qrgEncoder.getBitmap();
    }
}
