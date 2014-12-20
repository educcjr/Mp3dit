package controller;

import com.mpatric.mp3agic.Mp3File;
import model.TagEnum;

import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        Mp3FileController controller = new Mp3FileController("C:/Users/duds410/Desktop");

        Mp3File mp3File = controller.mp3FileFactory("Edu Sereno - Agenda.mp3");

        System.out.println(controller.getMp3Tag(mp3File, TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ARTIST));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ALBUM));

        /*if(controller.setMp3Tag(mp3File,TagEnum.TITLE,"TESTE"))
        {
            controller.saveMp3AndBackup(mp3File,"old");
            System.out.println(controller.getMp3Tag(mp3File, TagEnum.TITLE));
            System.out.println(controller.getMp3Tag(mp3File, TagEnum.ARTIST));
            System.out.println(controller.getMp3Tag(mp3File, TagEnum.ALBUM));
        }*/

        controller.updateID3ToV23(mp3File);
        mp3File = controller.mp3FileFactory("v23"+new File(mp3File.getFilename()));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ARTIST));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ALBUM));
    }
}
