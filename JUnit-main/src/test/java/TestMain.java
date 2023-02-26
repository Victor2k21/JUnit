import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;


public class TestMain {

    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Test
    @DisplayName("проверка создания файла")
    public void makeFileTest() throws IOException {
        temporaryFolder.create();
        File file = temporaryFolder.newFile("some.java");
        Main.makeFile(file);
        Assertions.assertTrue(file.exists());

    }

    @Test
    @DisplayName("тест на создание директории")
    public void makeDirectoryTest() throws IOException{
        temporaryFolder.create();
        File directory = temporaryFolder.newFile("work");
        Main.makeDirectory(directory);
        Assertions.assertTrue(directory.exists());
    }

    @Test
    @DisplayName("тест записи сохранения игры в файл Dat")
    public void saveGameTest() throws IOException{
        temporaryFolder.create();
        GameProgress gameProgress = new GameProgress(100,1,3,3.45);
        File testFile = temporaryFolder.newFile("file.dat");
        Main.makeFile(testFile);

        Main.saveGame(gameProgress,testFile.getAbsolutePath());
        Assertions.assertTrue(testFile.exists());
        Assertions.assertTrue(testFile.canRead());
        Assertions.assertTrue(testFile.canWrite());
        System.out.println(testFile.getName() + " существует");
        System.out.println("файл можно прочитать и можно записать");


      testFile.deleteOnExit();
    }
}
