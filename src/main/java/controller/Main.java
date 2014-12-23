package controller;

import com.mpatric.mp3agic.Mp3File;
import model.Reference;
import model.TagEnum;

public class Main
{
    public static void main(String[] args)
    {
        /* Tests */

        Mp3FileController controller = new Mp3FileController("C:/Users/ecastro/Desktop");

        Mp3File mp3file = controller.mp3FileFactory("Mastodon - Motherload.mp3");

        /*controller.updateID3ToV23(mp3file);

        if(mp3file.hasId3v1Tag())
        {
            System.out.println("ID3v1:");
            System.out.println(mp3file.getId3v1Tag().getTitle());
            System.out.println(mp3file.getId3v1Tag().getArtist());
        }
        System.out.println("ID3v2:");
        System.out.println(controller.getMp3Tag(mp3file, TagEnum.TITLE));
        System.out.println(controller.getMp3Tag(mp3file, TagEnum.ARTIST));*/

        controller.setFilenameAsTag(mp3file, new Reference('-'), TagEnum.ALBUMARTIST);
        controller.setFilenameAsTag(mp3file, new Reference('-',true), TagEnum.TITLE);
    }
}
