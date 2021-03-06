package Preservation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static String FILEPATH = "C:/Users/503242115/IdeaProjects/Initial/Games/savegames" + File.separator;
    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 1, 5, 50);
        GameProgress save2 = new GameProgress(78, 10, 48, 2452.96);
        GameProgress save3 = new GameProgress(5, 31, 107, 9963.27);

        List<String> files = new ArrayList<>();
        files.add(saveGame(FILEPATH + "save1.dat", save1));
        files.add(saveGame(FILEPATH + "save2.dat", save2));
        files.add(saveGame(FILEPATH + "save3.dat", save3));


        zipFiles(FILEPATH + "zip.zip", files);

    }

    public static String saveGame(String filename, GameProgress save) {
        try ( ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(save);
            System.out.println(">> Saving " + save + " was created successfully");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return filename;
    }

    public static void zipFiles(String zipName, List<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipName))) {
            for (String fileName : files) {
                FileInputStream fis = new FileInputStream(fileName);
                ZipEntry entry = new ZipEntry(fileName);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                System.out.println(">> Zipping " + fileName + " was done successfully");
                // закрываем текущую запись для новой записи
                zout.closeEntry();
                fis.close();
            }
            delete(files);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void delete(List<String> files) {
        try {
            for (String fileName : files) {
                if (Files.deleteIfExists(Paths.get(fileName))) {
                    System.out.println(">> File " + fileName + " was deleted");
                } else System.out.println(">> File " + fileName + " was not found!!!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}