package com.lazerycode.selenium.download;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExtractFilesFromZip {

    private static final Logger LOG = Logger.getLogger(ExtractFilesFromZip.class);

    /**
     * Unzip a downloaded zip file (this will implicitly overwrite any existing files)
     *
     * @param downloadedZip
     * @param extractedToFilePath
     * @return
     * @throws IOException
     */
    public static void unzipFile(File downloadedZip, String extractedToFilePath, boolean overwriteFilesThatExist) throws IOException {

        ZipFile zip = new ZipFile(downloadedZip);
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipFileEntry = entries.nextElement();
            if (zipFileEntry.isDirectory()) continue;
            File extractedFile = new File(extractedToFilePath, zipFileEntry.getName());
            if(extractedFile.exists() && !overwriteFilesThatExist) continue;
            extractedFile.getParentFile().mkdirs();
            extractedFile.createNewFile();
            InputStream is = zip.getInputStream(zipFileEntry);
            OutputStream os = new FileOutputStream(extractedFile);
            while (is.available() > 0) {
                os.write(is.read());
            }
            os.close();
            is.close();
        }
        zip.close();
    }
}