package com.staf.utilities.filehandling;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class that hold the file handling utilities
 */
public class FileHandle {

    /**
     * Logger
     */
    Logger log = Logger.getLogger(FileHandle.class);

    /**
     * Takes a copy of the source file
     *
     * @param srFile - Source file name with path
     * @param dtFile - Expected Back up file name with path
     */
    public void copyFile(String srFile, String dtFile) {
        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
            log.info("File copied.");
        } catch (FileNotFoundException ex) {
            log.info(ex.getMessage());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Deletes the file
     *
     * @param file - File to be deleted
     */
    public void deleteFile(String file) {
        File f1 = new File(file);
        boolean success = f1.delete();

        if (!success) {
            log.info("Deletion failed.");
        } else {
            log.info("File deleted.");
        }
    }

    /**
     * Compress folder
     *
     * @param sourceFolderPath           - Folder to be compressed
     * @param destZipFilePathWithZipExtn - Expected compressed file name with path
     */
    public void zipThisFolder(String sourceFolderPath, String destZipFilePathWithZipExtn) {
        try {
            File inFolder = new File(sourceFolderPath);
            File outFolder = new File(destZipFilePathWithZipExtn);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
            BufferedInputStream in;
            byte[] data = new byte[1000];
            String[] files = inFolder.list();

            for (String file : files) {
                in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + file), 1000);
                out.putNextEntry(new ZipEntry(file));
                int count;
                while ((count = in.read(data, 0, 1000)) != -1) {
                    out.write(data, 0, count);
                }
                out.closeEntry();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a back up of the source file
     *
     * @param sourceFilePath - Source file name with path
     * @param destFilePath   - Expected Back up file name with path
     */
    public void backUpFile(String sourceFilePath, String destFilePath) {

        File destinationFile = new File(destFilePath);
        if (destinationFile.exists()) {
            deleteFile(destFilePath);
            log.info(destinationFile.getName() + " - already existing. Deleting the already existing file.");
            copyFile(sourceFilePath, destFilePath);
            log.info("Back up successfully completed");
        } else {
            copyFile(sourceFilePath, destFilePath);
            log.info("Back up successfully completed");
        }
    }
}
