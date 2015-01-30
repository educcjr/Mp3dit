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
        Mp3FolderController folderController = new Mp3FolderController("C:/Users/duds410/Desktop/mp3tests");

        ArrayList<Mp3File> mp3FileList = folderController.getAllMp3Files();

        folderController.updateID3ToV23(mp3FileList);
        folderController.saveMp3AndBackup(mp3FileList,"obsolete");

        mp3FileList = folderController.getAllMp3Files();
        if (folderController.setFilenameAsTag(mp3FileList,new Reference('-'),ID3v2Tag.ALBUMARTIST) &&
            folderController.setFilenameAsTag(mp3FileList,new Reference('-',true),ID3v2Tag.TITLE))
        {
            folderController.saveMp3AndBackup(mp3FileList,"bak");
            //folderController.saveMp3(mp3FileList);
        }
    }
}
