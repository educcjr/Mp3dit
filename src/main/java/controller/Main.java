package controller;

import model.TagEnum;

public class Main
{
    public static void main(String[] args)
    {
        Mp3FileController controller = new Mp3FileController("C:/Users/duds410/Desktop");

        String fileName = "Edu Sereno - Agenda.mp3";

        System.out.println(controller.getMp3Tag(fileName, TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(fileName, TagEnum.ARTIST));
        System.out.println(controller.getMp3Tag(fileName, TagEnum.ALBUM));

        /*if(controller.setMp3Tag(fileName,TagEnum.TITLE,"TESTE"))
        {
            controller.saveMp3AndBackup(fileName,"old");
            System.out.println(controller.getMp3Tag(fileName, TagEnum.TITLE));
            System.out.println(controller.getMp3Tag(fileName, TagEnum.ARTIST));
            System.out.println(controller.getMp3Tag(fileName, TagEnum.ALBUM));
        }*/

        controller.updateID3ToV24(fileName);
        System.out.println(controller.getMp3Tag(fileName, TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(fileName, TagEnum.ARTIST));
        System.out.println(controller.getMp3Tag(fileName, TagEnum.ALBUM));
    }
}
