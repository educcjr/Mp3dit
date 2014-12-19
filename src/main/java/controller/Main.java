package controller;

import model.TagEnum;

public class Main
{
    public static void main(String[] args)
    {
        Mp3FileController controller = new Mp3FileController("C:/Users/ecastro/Desktop");

        controller.setMp3File("MusicaTeste.mp3");
        System.out.println(controller.getMp3Tag(TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(TagEnum.TRACK));
        System.out.println(controller.getMp3Tag(TagEnum.GENRE));
        System.out.println(controller.getMp3Tag(TagEnum.GENREDESCRIPTION));

        controller.setMp3Tag(TagEnum.TITLE, "ETA");
        controller.setMp3Tag(TagEnum.TRACK,"3");
        if(controller.saveMp3())
        {
            System.out.println(controller.getMp3Tag(TagEnum.TITLE));
            System.out.println(controller.getMp3Tag(TagEnum.TRACK));
            System.out.println(controller.getMp3Tag(TagEnum.GENRE));
            System.out.println(controller.getMp3Tag(TagEnum.GENREDESCRIPTION));
        }


    }
}
