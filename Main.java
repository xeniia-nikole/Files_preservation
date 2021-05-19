package Preservation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 1, 5, 50);
        GameProgress save2 = new GameProgress(78, 10, 48, 2452.96);
        GameProgress save3 = new GameProgress(5, 31, 107, 9963.27);

        saveGame("C:/Users/503242115/Games/savegames/save1.dat", save1);
        saveGame("C:/Users/503242115/Games/savegames/save2.dat", save2);
        saveGame("C:/Users/503242115/Games/savegames/save3.dat", save3);

        List<String> files = new ArrayList<>();
        files.add("C:/Users/503242115/Games/savegames/save1.dat");
        files.add("C:/Users/503242115/Games/savegames/save2.dat");
        files.add("C:/Users/503242115/Games/savegames/save3.dat");

        zipFiles("C:/Users/503242115/Games/savegames/zip.zip", files);


    }

    public static void saveGame(String path, GameProgress save) {
        try (FileOutputStream fos =
                     new FileOutputStream(path);
             ObjectOutputStream oos =
                     new ObjectOutputStream(fos)) {
            oos.writeObject(save);
            System.out.println(">> Saving " + save + " was done successfully");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipName, List<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipName));) {
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
                File file = new File(fileName);
                if (file.delete()) {
                    System.out.println(">> File " + fileName + " was deleted");
                } else System.out.println(">> File " + fileName + " was not found!!!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}