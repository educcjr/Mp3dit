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

    // Contrutor define o caminho atual
    public Mp3FileController(String workPath)
    {
        this.workPath = workPath +"/";
    }

    // Retorna um objeto Mp3File
    public Mp3File mp3FileFactory(String fileName)
    {
        Mp3File mp3file = null;

        try
        {
            mp3file = new Mp3File(this.workPath+fileName);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException(mp3FileFactory): "+ioe.getMessage());
        }
        catch (UnsupportedTagException ute)
        {
            System.out.println("UnsupportedTagException(mp3FileFactory): "+ute.getMessage());
        }
        catch(InvalidDataException ide)
        {
            System.out.println("InvalidDataException(mp3FileFactory): "+ide.getMessage());
        }

        return mp3file;
    }

    // Verifica se um objeto do tipo File existe
    public boolean fileExists(Mp3File mp3File)
    {
        boolean r = false;

        if (mp3File!=null)
        {
            File file = new File(mp3File.getFilename());
            if (file.exists())
            {
                r = true;
            }
            else
            {
                System.out.println("Arquivo inexistente. ("+this.workPath +file.getName()+")");
            }
        }

        return r;
    }

    // Atualiza ID3 de qualquer versão para ID3v23 (adiciona caso não tenha)
    public void updateID3ToV23(Mp3File mp3File)
    {
        if(mp3File.hasId3v1Tag())
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

            ID3v2 id3v23Tag = new ID3v23Tag();
            id3v23Tag.setTrack(v1Track);
            id3v23Tag.setArtist(v1Artist);
            id3v23Tag.setTitle(v1Title);
            id3v23Tag.setAlbum(v1Album);
            id3v23Tag.setYear(v1Year);
            id3v23Tag.setGenre(v1Genre);
            id3v23Tag.setComment(v1Comment);
            mp3File.setId3v2Tag(id3v23Tag);

            System.out.println("ID3v1 atualizado para ID3v23.");
        }
        else if(mp3File.hasId3v2Tag())
        {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            String v2Track = id3v2Tag.getTrack();
            String v2Artist = id3v2Tag.getArtist();
            String v2Title = id3v2Tag.getTitle();
            String v2Album = id3v2Tag.getAlbum();
            String v2Year = id3v2Tag.getYear();
            int v2Genre = id3v2Tag.getGenre();
            String v2GenreDescription = id3v2Tag.getGenreDescription();
            String v2Comment = id3v2Tag.getComment();
            String v2Composer = id3v2Tag.getComposer();
            String v2Publisher = id3v2Tag.getPublisher();
            String v2OriginalArtist = id3v2Tag.getOriginalArtist();
            String v2AlbumArtist = id3v2Tag.getAlbumArtist();
            String v2Copyright = id3v2Tag.getCopyright();
            String v2Url = id3v2Tag.getUrl();
            String v2Encoder = id3v2Tag.getEncoder();
            mp3File.removeId3v2Tag();

            ID3v2 id3v23Tag = new ID3v23Tag();
            id3v23Tag.setTrack(v2Track);
            id3v23Tag.setArtist(v2Artist);
            id3v23Tag.setTitle(v2Title);
            id3v23Tag.setAlbum(v2Album);
            id3v23Tag.setYear(v2Year);
            id3v23Tag.setGenre(v2Genre);
            try
            {
                id3v23Tag.setGenreDescription(v2GenreDescription);
            }
            catch (IllegalArgumentException iae)
            {
                System.out.println("IllegalArgumentException(updateID3ToV23): "+iae.getMessage());
                // TODO: Métodos recebem Mp3File e não String
            }
            id3v23Tag.setComment(v2Comment);
            id3v23Tag.setComposer(v2Composer);
            id3v23Tag.setPublisher(v2Publisher);
            id3v23Tag.setOriginalArtist(v2OriginalArtist);
            id3v23Tag.setAlbumArtist(v2AlbumArtist);
            id3v23Tag.setCopyright(v2Copyright);
            id3v23Tag.setUrl(v2Url);
            id3v23Tag.setEncoder(v2Encoder);
            mp3File.setId3v2Tag(id3v23Tag);

            System.out.println("ID3v2x atualizado para ID3v23.");
        }
        else
        {
            ID3v2 id3v23Tag = new ID3v23Tag();
            mp3File.setId3v2Tag(id3v23Tag);

            System.out.println("ID3v23 adicionado.");
        }

        File file = new File(mp3File.getFilename());
        saveMp3(mp3File,"v23"+file.getName());
    }

    // Retorna tag escolhida de um mp3 (ID3v2x)
    public String getMp3Tag(Mp3File mp3File, TagEnum tag)
    {
        String mp3Tag = null;

        if (fileExists(mp3File) && mp3File.hasId3v2Tag())
        {
            ID3v2 mp3ID3v2Tag = mp3File.getId3v2Tag();
            switch (tag)
            {
                case TRACK:
                    mp3Tag = mp3ID3v2Tag.getTrack();
                    break;
                case ARTIST:
                    mp3Tag = mp3ID3v2Tag.getArtist();
                    break;
                case TITLE:
                    mp3Tag = mp3ID3v2Tag.getTitle();
                    break;
                case ALBUM:
                    mp3Tag = mp3ID3v2Tag.getAlbum();
                    break;
                case YEAR:
                    mp3Tag = mp3ID3v2Tag.getYear();
                    break;
                case GENRE:
                    mp3Tag = String.valueOf(mp3ID3v2Tag.getGenre());
                    break;
                case GENREDESCRIPTION:
                    mp3Tag = mp3ID3v2Tag.getGenreDescription();
                    break;
                case COMMENT:
                    mp3Tag = mp3ID3v2Tag.getComment();
                    break;
                case COMPOSER:
                    mp3Tag = mp3ID3v2Tag.getComposer();
                    break;
                case PUBLISHER:
                    mp3Tag = mp3ID3v2Tag.getPublisher();
                    break;
                case ORIGINALARTIST:
                    mp3Tag = mp3ID3v2Tag.getOriginalArtist();
                    break;
                case ALBUMARTIST:
                    mp3Tag = mp3ID3v2Tag.getAlbumArtist();
                    break;
                case COPYRIGHT:
                    mp3Tag = mp3ID3v2Tag.getCopyright();
                    break;
                case URL:
                    mp3Tag = mp3ID3v2Tag.getUrl();
                    break;
                case ENCODER:
                    mp3Tag = mp3ID3v2Tag.getEncoder();
                    break;
            }
        }

        return mp3Tag;
    }

    // Altera qualquer tag do mp3 global (ID3v23 e ID3v24)
    public boolean setMp3Tag(Mp3File mp3File, TagEnum tag, String newTag)
    {
        boolean r = false;

        if(fileExists(mp3File) && mp3File.hasId3v2Tag() && newTag!=null)
        {
            ID3v2 mp3ID3v2Tag = mp3File.getId3v2Tag();
            switch (tag)
            {
                case TRACK:
                    mp3ID3v2Tag.setTrack(newTag);
                    break;
                case ARTIST:
                    mp3ID3v2Tag.setArtist(newTag);
                    break;
                case TITLE:
                    mp3ID3v2Tag.setTitle(newTag);
                    break;
                case ALBUM:
                    mp3ID3v2Tag.setAlbum(newTag);
                    break;
                case YEAR:
                    mp3ID3v2Tag.setYear(newTag);
                    break;
                case GENRE:
                    mp3ID3v2Tag.setGenre(Integer.parseInt(newTag));
                    break;
                case GENREDESCRIPTION:
                    mp3ID3v2Tag.setGenreDescription(newTag);
                    break;
                case COMMENT:
                    mp3ID3v2Tag.setComment(newTag);
                    break;
                case COMPOSER:
                    mp3ID3v2Tag.setComposer(newTag);
                    break;
                case PUBLISHER:
                    mp3ID3v2Tag.setPublisher(newTag);
                    break;
                case ORIGINALARTIST:
                    mp3ID3v2Tag.setOriginalArtist(newTag);
                    break;
                case ALBUMARTIST:
                    mp3ID3v2Tag.setAlbumArtist(newTag);
                    break;
                case COPYRIGHT:
                    mp3ID3v2Tag.setCopyright(newTag);
                    break;
                case URL:
                    mp3ID3v2Tag.setUrl(newTag);
                    break;
                case ENCODER:
                    mp3ID3v2Tag.setEncoder(newTag);
                    break;
            }

            System.out.println(tag.name()+" tag alterada. ("+mp3File.getFilename()+")");
            r = true;
        }

        return r;
    }

    // Salva novo mp3 e guarda o antigo em pasta backup
    public boolean saveMp3AndBackup(Mp3File mp3File, String backupDirectory)
    {
        boolean r = false;

        File file = new File(mp3File.getFilename());

        if (fileExists(mp3File))
        {
            if (new File(this.workPath +backupDirectory).mkdirs())
            {
                System.out.println("Diretório criado");
            }

            try
            {
                mp3File.save(this.workPath +backupDirectory+"/"+file.getName());
                Files.move(Paths.get(this.workPath + file.getName()), Paths.get(this.workPath + backupDirectory + "/" + file.getName().substring(0, file.getName().length() - 4) + "-old.mp3"), StandardCopyOption.REPLACE_EXISTING);
                Files.move(Paths.get(this.workPath +backupDirectory+"/"+file.getName()),Paths.get(this.workPath +file.getName()), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Novo mp3 salvo e backup criado com sucesso.");
                r = true;
            }
            catch(IOException ioe)
            {
                System.out.println("IOException(saveMp3AndBackup): "+ioe.getMessage());
            }
            catch(NotSupportedException nse)
            {
                System.out.println("NotSupportedException(saveMp3AndBackup): "+nse.getMessage());
            }
        }

        return r;
    }

    // Salva um mp3 com um novo nome
    public boolean saveMp3(Mp3File mp3File, String newFileName)
    {
        boolean r = false;

        if (fileExists(mp3File))
        {
            try
            {
                mp3File.save(this.workPath+newFileName);

                System.out.println("Novo mp3 salvo. ("+this.workPath+newFileName+")");
                r = true;
            }
            catch(IOException ioe)
            {
                System.out.println("IOException(saveMp3): "+ioe.getMessage());
            }
            catch(NotSupportedException nse)
            {
                System.out.println("NotSupportedException(saveMp3): "+nse.getMessage());
            }
        }

        return r;
    }
}
