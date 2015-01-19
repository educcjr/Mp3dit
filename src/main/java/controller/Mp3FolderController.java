package controller;

import com.mpatric.mp3agic.Mp3File;
import model.ID3v2Tag;
import model.Reference;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Mp3FolderController {
    private Mp3FileController controller;

    public Mp3FolderController(String workPath) {
        if (Files.exists(Paths.get(workPath))) {
            controller = new Mp3FileController(workPath);
        } else {
            System.out.println("Caminho inexistente. (" + workPath + ")");
        }
    }

    // Retorna uma lista de Mp3File a partir dos .mp3 de uma pasta (subpastas são ignoradas)
    public ArrayList<Mp3File> getAllMp3Files() {
        ArrayList<Mp3File> mp3FileList = new ArrayList<>();

        String workPath = controller.getWorkPath();
        if (workPath != null) {
            try {
                Files.walk(Paths.get(workPath), 1).forEach(filePath ->
                {
                    if (Files.isRegularFile(filePath)) {
                        String fileName = filePath.toString();
                        if (FilenameUtils.getExtension(fileName).equals("mp3")) {
                            System.out.println(filePath);
                            mp3FileList.add(controller.mp3FileFactory(filePath.getFileName().toString()));
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mp3FileList;
    }

    public boolean setFilenameAsTag(ArrayList<Mp3File> mp3FileList, Reference reference, ID3v2Tag id3v2Tag) {
        boolean r = true;

        for (Mp3File mp3File : mp3FileList) {
            if (!controller.setFilenameAsTag(mp3File, reference, id3v2Tag)) {
                r = false;
            }
        }

        return r;
    }

    public boolean saveMp3(ArrayList<Mp3File> mp3FileList) {
        boolean r = true;

        for (Mp3File mp3File : mp3FileList) {
            if (!controller.saveMp3(mp3File)) {
                r = false;
            }
        }

        return r;
    }

    public boolean saveMp3AndBackup(ArrayList<Mp3File> mp3FileList, String backupFolder) {
        boolean r = true;

        for (Mp3File mp3File : mp3FileList) {
            if (!controller.saveMp3AndBackup(mp3File, backupFolder)) {
                r = false;
            }
        }

        return r;
    }

    public void updateID3ToV23(ArrayList<Mp3File> mp3FileList) {
        mp3FileList.forEach(controller::updateID3ToV23);
    }
}
