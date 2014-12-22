package controller;

import com.mpatric.mp3agic.Mp3File;

public class Main
{
    public static void main(String[] args)
    {
        Mp3FileController controller = new Mp3FileController("C:/Users/ecastro/Desktop");

        /*Mp3File mp3File = controller.mp3FileFactory("Edu Sereno - Agenda.mp3");

        System.out.println(controller.getMp3Tag(mp3File, TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ARTIST));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ALBUM));

        if(controller.setMp3Tag(mp3File,TagEnum.TITLE,"TESTE"))
        {
            controller.saveMp3AndBackup(mp3File,"old");
            System.out.println(controller.getMp3Tag(mp3File, TagEnum.TITLE));
            System.out.println(controller.getMp3Tag(mp3File, TagEnum.ARTIST));
            System.out.println(controller.getMp3Tag(mp3File, TagEnum.ALBUM));
        }

        controller.updateID3ToV23(mp3File);
        mp3File = controller.mp3FileFactory("v23"+new File(mp3File.getFilename()).getName());
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ARTIST));
        System.out.println(controller.getMp3Tag(mp3File, TagEnum.ALBUM));
        controller.setMp3Tag(mp3File,TagEnum.GENREDESCRIPTION,"Música Brasileira");
        controller.saveMp3(mp3File,"newGenreDesc"+new File(mp3File.getFilename()).getName());*/

        Mp3File mp3file = controller.mp3FileFactory("MusicaTeste.mp3");

        controller.updateID3ToV23(mp3file);

        if(mp3file.hasId3v1Tag())
        {
            System.out.println(mp3file.getId3v1Tag().getVersion());
            System.out.println(mp3file.getId3v1Tag().getGenreDescription());
        }

        controller.saveMp3AndBackup(mp3file,"bak");
    }
}
