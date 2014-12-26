package controller;

import com.mpatric.mp3agic.Mp3File;
import model.Reference;
import model.ID3v2Tag;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        /* Testes */
        Mp3FolderController controller = new Mp3FolderController("C:/Users/ecastro/Desktop/mp3dit tests");

        ArrayList<Mp3File> mp3FileList = controller.getAllMp3Files();
        if (controller.setFilenameAsTag(mp3FileList,new Reference('-'),ID3v2Tag.ALBUMARTIST) &&
            controller.setFilenameAsTag(mp3FileList,new Reference('-',true),ID3v2Tag.TITLE))
        {
            controller.saveMp3AndBackup(mp3FileList,"bak");
        }
    }
}
