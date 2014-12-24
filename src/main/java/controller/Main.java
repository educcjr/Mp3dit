package controller;

import com.mpatric.mp3agic.Mp3File;
import model.Reference;
import model.ID3v2Tag;

public class Main
{
    public static void main(String[] args)
    {
        /* Tests */
        Mp3FileController controller = new Mp3FileController("C:/Users/duds410/Desktop");
        String mp3name = "Edu Sereno - Agenda.mp3";
        Mp3File mp3file = controller.mp3FileFactory(mp3name);

        if (controller.updateID3ToV23(mp3file))
        {
            controller.saveMp3AndBackup(mp3file, "obsolete mp3");
            mp3file = controller.mp3FileFactory(mp3name);
        }

        if (controller.setFilenameAsTag(mp3file, new Reference('-'), ID3v2Tag.ALBUMARTIST) &&
            controller.setFilenameAsTag(mp3file, new Reference('-', true), ID3v2Tag.TITLE))
        {
            controller.saveMp3AndBackup(mp3file, "bak");
        }

        controller.setMp3Tag(mp3file,ID3v2Tag.ALBUM,null);
    }
}
