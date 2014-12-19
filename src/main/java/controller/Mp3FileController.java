package controller;

import com.mpatric.mp3agic.*;
import model.TagEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Mp3FileController
{
    private String workPath;
    private File file;
    private Mp3File mp3File;
    private ID3v2 mp3ID3Tag;

    // Contrutor define o caminho global
    public Mp3FileController(String workPath)
    {
        this.workPath = workPath +"/";
        this.file = new File("empty");
    }

    // Seleciona mp3 a ser editado e adiciona ou atualiza tags (ID3v24)
    public boolean setMp3File(String fileName)
    {
        boolean r = false;

        try
        {
            String fileLocation = this.workPath +fileName;
            this.file = new File(fileLocation);
            this.mp3File = new Mp3File(fileLocation);

            if (!this.mp3File.hasId3v2Tag())
            {
                if (this.mp3File.hasId3v1Tag())
                {
                    if(updateV1ToV24())
                    {
                        System.out.println("Tags ID3v1 atualizadas para ID3v24.");
                        r = true;
                    }
                }
                else
                {
                    ID3v2 id3v2Tag = new ID3v24Tag();
                    this.mp3File.setId3v2Tag(id3v2Tag);

                    System.out.println("Tags ID3v24 adicionadas.");
                    r = true;
                }
            }

            this.mp3ID3Tag = this.mp3File.getId3v2Tag();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException(setMp3File): "+ioe.getMessage());
        }
        catch (UnsupportedTagException ute)
        {
            System.out.println("UnsupportedTagException(setMp3File): "+ute.getMessage());
        }
        catch(InvalidDataException ide)
        {
            System.out.println("InvalidDataException(setMp3File): "+ide.getMessage());
        }

        return r;
    }

    // Verifica se o mp3 global (File) existe
    public boolean fileExists()
    {
        boolean r = false;

        if (this.file.exists())
        {
            r = true;
        }
        else
        {
            System.out.println("Arquivo inexistente. ("+this.workPath +this.file.getName()+")");
        }

        return r;
    }

    // Atualiza ID3v1 para ID3v24
    public boolean updateV1ToV24()
    {
        boolean r = false;

        if (mp3File.hasId3v1Tag())
        {
            ID3v1 id3v1Tag = mp3File.getId3v1Tag();
            String v1Track = id3v1Tag.getTrack();
            String v1Artist = id3v1Tag.getArtist();
            String v1Title = id3v1Tag.getTitle();
            String v1Album = id3v1Tag.getAlbum();
            String v1Year = id3v1Tag.getYear();
            int v1Genre = id3v1Tag.getGenre();
            String v1Comment = id3v1Tag.getComment();

            mp3File.removeId3v1Tag();

            ID3v2 id3v2Tag = new ID3v24Tag();
            id3v2Tag.setTrack(v1Track);
            id3v2Tag.setArtist(v1Artist);
            id3v2Tag.setTitle(v1Title);
            id3v2Tag.setAlbum(v1Album);
            id3v2Tag.setYear(v1Year);
            id3v2Tag.setGenre(v1Genre);
            id3v2Tag.setComment(v1Comment);

            mp3File.setId3v2Tag(id3v2Tag);

            r = true;
        }

        return r;
    }

    // Retorna qualquer tag do mp3 global (ID3v2)
    public String getMp3Tag(TagEnum tag)
    {
        String mp3Tag = null;

        if (fileExists())
        {
            switch (tag)
            {
                case TRACK:
                    mp3Tag = this.mp3ID3Tag.getTrack();
                    break;
                case ARTIST:
                    mp3Tag = this.mp3ID3Tag.getArtist();
                    break;
                case TITLE:
                    mp3Tag = this.mp3ID3Tag.getTitle();
                    break;
                case ALBUM:
                    mp3Tag = this.mp3ID3Tag.getAlbum();
                    break;
                case YEAR:
                    mp3Tag = this.mp3ID3Tag.getYear();
                    break;
                case GENRE:
                    mp3Tag = String.valueOf(this.mp3ID3Tag.getGenre());
                    break;
                case GENREDESCRIPTION:
                    mp3Tag = this.mp3ID3Tag.getGenreDescription();
                    break;
                case COMMENT:
                    mp3Tag = this.mp3ID3Tag.getComment();
                    break;
                case COMPOSER:
                    mp3Tag = this.mp3ID3Tag.getComposer();
                    break;
                case PUBLISHER:
                    mp3Tag = this.mp3ID3Tag.getPublisher();
                    break;
                case ORIGINALARTIST:
                    mp3Tag = this.mp3ID3Tag.getOriginalArtist();
                    break;
                case ALBUMARTIST:
                    mp3Tag = this.mp3ID3Tag.getAlbumArtist();
                    break;
                case COPYRIGHT:
                    mp3Tag = this.mp3ID3Tag.getCopyright();
                    break;
                case URL:
                    mp3Tag = this.mp3ID3Tag.getUrl();
                    break;
                case ENCODER:
                    mp3Tag = this.mp3ID3Tag.getEncoder();
                    break;
            }
        }

        return mp3Tag;
    }

    // Altera qualquer tag do mp3 global (ID3v2)
    public boolean setMp3Tag(TagEnum tag, String newTag)
    {
        boolean r = false;

        if (fileExists())
        {
            switch (tag)
            {
                case TRACK:
                    this.mp3ID3Tag.setTrack(newTag);
                    break;
                case ARTIST:
                    this.mp3ID3Tag.setArtist(newTag);
                    break;
                case TITLE:
                    this.mp3ID3Tag.setTitle(newTag);
                    break;
                case ALBUM:
                    this.mp3ID3Tag.setAlbum(newTag);
                    break;
                case YEAR:
                    this.mp3ID3Tag.setYear(newTag);
                    break;
                case GENRE:
                    this.mp3ID3Tag.setGenre(Integer.parseInt(newTag));
                    break;
                case GENREDESCRIPTION:
                    this.mp3ID3Tag.setGenreDescription(newTag);
                    break;
                case COMMENT:
                    this.mp3ID3Tag.setComment(newTag);
                    break;
                case COMPOSER:
                    this.mp3ID3Tag.setComposer(newTag);
                    break;
                case PUBLISHER:
                    this.mp3ID3Tag.setPublisher(newTag);
                    break;
                case ORIGINALARTIST:
                    this.mp3ID3Tag.setOriginalArtist(newTag);
                    break;
                case ALBUMARTIST:
                    this.mp3ID3Tag.setAlbumArtist(newTag);
                    break;
                case COPYRIGHT:
                    this.mp3ID3Tag.setCopyright(newTag);
                    break;
                case URL:
                    this.mp3ID3Tag.setUrl(newTag);
                    break;
                case ENCODER:
                    this.mp3ID3Tag.setEncoder(newTag);
                    break;
            }

            r = true;
        }

        return r;
    }

    // Salva novo mp3 e guarda o antigo em pasta backup
    public boolean saveMp3()
    {
        boolean r = false;

        if (fileExists())
        {
            File file = this.file;
            String backupDirectory = "old";

            if(new File(this.workPath +backupDirectory).mkdirs())
            {
                System.out.println("Diretório criado");
            }

            try
            {
                mp3File.save(this.workPath +backupDirectory+"/"+file.getName());
                Files.move(Paths.get(this.workPath +file.getName()),Paths.get(this.workPath +backupDirectory+"/"+file.getName().substring(0,file.getName().length()-4)+"-old.mp3"), StandardCopyOption.REPLACE_EXISTING);
                Files.move(Paths.get(this.workPath +backupDirectory+"/"+file.getName()),Paths.get(this.workPath +file.getName()), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Novo mp3 salvo e backup criado com sucesso.");
                r = true;
            }
            catch(IOException ioe)
            {
                System.out.println("IOException(saveMp3): "+ioe.getMessage());
            }
            catch (NotSupportedException nse)
            {
                System.out.println("NotSupportedException(saveMp3): "+nse.getMessage());
            }
        }

        return r;
    }
}
