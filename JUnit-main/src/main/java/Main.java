import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        List<File> game = new ArrayList<>();
        List<File> files = new ArrayList<>();

        //temp.txt для сохранений логов
        File temp_txt = new File("E://Games1/temp", "temp.txt");

        game.add(new File("E://Games1"));
        game.add(new File("E://Games1/src"));
        game.add(new File("E://Games1/res"));
        game.add(new File("E://Games1/savegames"));
        game.add(new File("E://Games1/temp"));

        //добавляем в src
        game.add(new File("E://Games1/src", "main"));
        game.add(new File("E://Games1/src", "test"));

        //добавляем в папку /games/src/main
        files.add(new File("E://Games1/src/main/Main.java"));
        files.add(new File("E://Games1/src/main/Utils.java"));

        //добавляем в /Games/res
        game.add(new File("E://Games1/res", "drawables"));
        game.add(new File("E://Games1/res", "vectors"));
        game.add(new File("E://Games1/res", "icons"));

        //temp.txt
        files.add(new File("E://Games1/temp", "temp.txt"));

        //цикл для добавления директорий
        for (File file : game) {
            makeDirectory(file);
            sb.append(makeDirectory(file));
        }
        //цикл для добавлений файлов
        for (File file : files) {
             makeFile(file);
            sb.append(makeFile(file));
        }

        // добавляем все логи в записную книгу temp.txt
        String one = sb.toString();
        byte[] buffer = one.getBytes();
        try (FileOutputStream writer = new FileOutputStream(temp_txt);
             BufferedOutputStream bos = new BufferedOutputStream(writer)) {
            bos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        String aga = "ну ты понял да";
        try (FileWriter nu = new FileWriter(temp_txt, true)) {
            nu.write(aga);
            nu.write("\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

//        //обьявление GameProgress
//
//        GameProgress first = new GameProgress(100, 1, 25, 100.50);
//        GameProgress second = new GameProgress(90, 2, 24, 98.5);
//        GameProgress third = new GameProgress(200, 3, 45, 3000.33);
        //используем метод для записи прогресса
        saveGame(new GameProgress(100, 1, 25, 100.50), "E://Games1/savegames/saveFirst.dat");
        saveGame(new GameProgress(90, 2, 24, 98.5), "E://Games1/savegames/saveSecond.dat");
        saveGame(new GameProgress(200, 3, 45, 3000.33), "E://Games1/savegames/saveThird.dat");

        //создание List для маршрутов файлов архивации
        List<String> dat = new ArrayList<>();
        dat.add("E://Games1/savegames/saveFirst.dat");
        dat.add("E://Games1/savegames/saveSecond.dat");
        dat.add("E://Games1/savegames/saveThird.dat");

        makeZip("E://Games1/savegames/zip.zip", dat);


    }
    public static String makeDirectory(File file) {
        if (!file.exists()) {
            if (file.mkdir()) {
                return "Файл " + file.getName() + " создан \n";
            } else {
                System.out.println("не получилось");
            }
        }
        return null;
    }

    public static String makeFile(File file) {
        try {
            if (file.createNewFile())
                return "Файл " + file.getName() + " создан \n";

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    // метод для создания прогресса для игры
    public static void saveGame(GameProgress pro, String str) {
        try (FileOutputStream fos = new FileOutputStream(str);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(pro);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Метод для архивации файлов ZIP
    public static void makeZip(String str,List<String> list)  {
        FileInputStream fis;
        try{ ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(str));

            for (String path: list) {
                File input = new File(path);
                fis = new FileInputStream(input);
                ZipEntry entry = new ZipEntry(input.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                zout.write(buffer);
                zout.flush();
                fis.close();
                input.deleteOnExit();
            }zout.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}