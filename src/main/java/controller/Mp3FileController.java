package controller;

import com.mpatric.mp3agic.*;
import model.Reference;
import model.ID3v2Tag;

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
        if (!mp3File.hasId3v2Tag() && !mp3File.hasId3v1Tag())
        {
            ID3v2 id3v23Tag = new ID3v23Tag();
            mp3File.setId3v2Tag(id3v23Tag);

            System.out.println("Mp3 não possui ID3. ID3v23 adicionado.");
            saveMp3AndBackup(mp3File,"Mp3 sem ID3");
        }
        else if(mp3File.hasId3v1Tag() && !mp3File.hasId3v2Tag())
        {
            String v1Track = getMp3Tag(mp3File, ID3v2Tag.TRACK);
            String v1Artist = getMp3Tag(mp3File, ID3v2Tag.ARTIST);
            String v1Title = getMp3Tag(mp3File, ID3v2Tag.TITLE);
            String v1Album = getMp3Tag(mp3File, ID3v2Tag.ALBUM);
            String v1Year = getMp3Tag(mp3File, ID3v2Tag.YEAR);
            String v1Genre = getMp3Tag(mp3File, ID3v2Tag.GENRE);
            String v1Comment = getMp3Tag(mp3File, ID3v2Tag.COMMENT);

            ID3v2 id3v23Tag = new ID3v23Tag();
            mp3File.setId3v2Tag(id3v23Tag);
            setMp3Tag(mp3File, ID3v2Tag.TRACK, v1Track);
            setMp3Tag(mp3File, ID3v2Tag.ARTIST, v1Artist);
            setMp3Tag(mp3File, ID3v2Tag.TITLE, v1Title);
            setMp3Tag(mp3File, ID3v2Tag.ALBUM, v1Album);
            setMp3Tag(mp3File, ID3v2Tag.YEAR, v1Year);
            setMp3Tag(mp3File, ID3v2Tag.GENRE, String.valueOf(v1Genre));
            setMp3Tag(mp3File, ID3v2Tag.COMMENT, v1Comment);

            System.out.println("Mp3 possui apenas ID3v1. ID3v23 adicionado.");
            saveMp3AndBackup(mp3File, "Mp3 ID3v1 apenas");
        }
        else if(mp3File.hasId3v2Tag() && mp3File.getId3v2Tag().getObseleteFormat())
        {
            String v2Track = getMp3Tag(mp3File, ID3v2Tag.TRACK);
            String v2Artist = getMp3Tag(mp3File, ID3v2Tag.ARTIST);
            String v2Title = getMp3Tag(mp3File, ID3v2Tag.TITLE);
            String v2Album = getMp3Tag(mp3File, ID3v2Tag.ALBUM);
            String v2Year = getMp3Tag(mp3File, ID3v2Tag.YEAR);
            String v2Genre = getMp3Tag(mp3File, ID3v2Tag.GENRE);
            String v2GenreDescription = getMp3Tag(mp3File, ID3v2Tag.GENREDESCRIPTION);
            String v2Comment = getMp3Tag(mp3File, ID3v2Tag.COMMENT);
            String v2Composer = getMp3Tag(mp3File, ID3v2Tag.COMPOSER);
            String v2Publisher = getMp3Tag(mp3File, ID3v2Tag.PUBLISHER);
            String v2OriginalArtist = getMp3Tag(mp3File, ID3v2Tag.ORIGINALARTIST);
            String v2AlbumArtist = getMp3Tag(mp3File, ID3v2Tag.ALBUMARTIST);
            String v2Copyright = getMp3Tag(mp3File, ID3v2Tag.COPYRIGHT);
            String v2Url = getMp3Tag(mp3File, ID3v2Tag.URL);
            String v2Encoder = getMp3Tag(mp3File, ID3v2Tag.ENCODER);
            mp3File.removeId3v2Tag();

            ID3v2 id3v23Tag = new ID3v23Tag();
            mp3File.setId3v2Tag(id3v23Tag);
            setMp3Tag(mp3File, ID3v2Tag.TRACK,v2Track);
            setMp3Tag(mp3File, ID3v2Tag.ARTIST,v2Artist);
            setMp3Tag(mp3File, ID3v2Tag.TITLE,v2Title);
            setMp3Tag(mp3File, ID3v2Tag.ALBUM,v2Album);
            setMp3Tag(mp3File, ID3v2Tag.YEAR,v2Year);
            setMp3Tag(mp3File, ID3v2Tag.GENRE,String.valueOf(v2Genre));
            setMp3Tag(mp3File, ID3v2Tag.GENREDESCRIPTION,v2GenreDescription);
            setMp3Tag(mp3File, ID3v2Tag.COMMENT,v2Comment);
            setMp3Tag(mp3File, ID3v2Tag.COMPOSER,v2Composer);
            setMp3Tag(mp3File, ID3v2Tag.PUBLISHER,v2Publisher);
            setMp3Tag(mp3File, ID3v2Tag.ORIGINALARTIST,v2OriginalArtist);
            setMp3Tag(mp3File, ID3v2Tag.ALBUMARTIST,v2AlbumArtist);
            setMp3Tag(mp3File, ID3v2Tag.COPYRIGHT,v2Copyright);
            setMp3Tag(mp3File, ID3v2Tag.URL,v2Url);
            setMp3Tag(mp3File, ID3v2Tag.ENCODER,v2Encoder);

            System.out.println("Mp3 possui ID3v22. Atualizado para ID3v23.");
            saveMp3AndBackup(mp3File, "Mp3 ID3v22");
        }
    }

    // Retorna tag escolhida de um mp3 (ID3v2x)
    public String getMp3Tag(Mp3File mp3File, ID3v2Tag tag)
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
    public boolean setMp3Tag(Mp3File mp3File, ID3v2Tag id3v2Tag, String newTag)
    {
        boolean r = false;

        if(fileExists(mp3File) && mp3File.hasId3v2Tag() && newTag!=null)
        {
            ID3v2 mp3ID3v2Tag = mp3File.getId3v2Tag();
            switch (id3v2Tag)
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
                    try
                    {
                        mp3ID3v2Tag.setGenreDescription(newTag);
                    }
                    catch (IllegalArgumentException iae)
                    {
                        System.out.println("IllegalArgumentException(updateID3ToV23): "+iae.getMessage());
                        r = true;
                    }
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

            if (r)
            {
                r = false;
            }
            else
            {
                System.out.println(id3v2Tag.name()+" tag alterada. ("+mp3File.getFilename()+")");
                r = true;
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

    // Salva novo mp3 e guarda o antigo em pasta backup
    public boolean saveMp3AndBackup(Mp3File mp3File, String backupFolder)
    {
        boolean r = false;

        File file = new File(mp3File.getFilename());
        backupFolder = backupFolder+"/";

        if (fileExists(mp3File))
        {
            if (new File(this.workPath+backupFolder).mkdirs())
            {
                System.out.println("Diretório criado");
            }

            try
            {
                saveMp3(mp3File, file.getName().substring(0, file.getName().length() - 4) + "NEW.mp3");
                Files.move(Paths.get(mp3File.getFilename()), Paths.get(this.workPath + backupFolder + file.getName()), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Backup criado com sucesso. (" + this.workPath + backupFolder + ")");

                file = new File(mp3File.getFilename().substring(0,mp3File.getFilename().length()-4)+"NEW.mp3");
                if (file.renameTo(new File(mp3File.getFilename())))
                {
                    System.out.println("Arquivo renomeado. ("+mp3File.getFilename()+")");
                }

                r = true;
            }
            catch(IOException ioe)
            {
                System.out.println("IOException(saveMp3AndBackup): "+ioe.getMessage());
            }
        }

        return r;
    }

    public void setFilenameAsTag(Mp3File mp3File, Reference reference, ID3v2Tag id3v2Tag)
    {
        File file = new File(mp3File.getFilename());

        String newTag;
        String filename = file.getName().substring(0,file.getName().length()-4);
        int beginSubstring = -1;
        int endSubstring = -1;

        if (!reference.isBefore())
        {
            int j=0;
            for (int i=0;i<filename.length();i++)
            {
                if (filename.charAt(i)==reference.getCharacter())
                {
                    j++;

                    if (j==reference.getPlace()-1)
                    {
                        beginSubstring = i+1;
                    }
                    if (j==reference.getPlace())
                    {
                        endSubstring = i;
                        if (reference.getPlace()==1)
                        {
                            beginSubstring = 0;
                        }
                        break;
                    }
                }
            }
            if (endSubstring == -1)
            {
                System.out.println("Begin index: "+beginSubstring);
                newTag = filename.substring(beginSubstring);
            }
            else
            {
                System.out.println("Begin index: "+beginSubstring);
                System.out.println("End index: "+endSubstring);
                newTag = filename.substring(beginSubstring,endSubstring);
            }
        }
        else
        {
            int j=0;
            for (int i=0;i<filename.length();i++)
            {
                if (filename.charAt(i)==reference.getCharacter())
                {
                    j++;

                    if (j==reference.getPlace())
                    {
                        beginSubstring = i+1;
                    }
                    if (j==reference.getPlace()+1)
                    {
                        endSubstring = i;
                        break;
                    }
                }
            }
            if (endSubstring == -1)
            {
                System.out.println("Begin index: "+beginSubstring);
                newTag = filename.substring(beginSubstring);
            }
            else
            {
                System.out.println("Begin index: "+beginSubstring);
                System.out.println("End index: "+endSubstring);
                newTag = filename.substring(beginSubstring,endSubstring);
            }
        }

        System.out.println("newTag a ser inserida: "+newTag);
        if(setMp3Tag(mp3File, id3v2Tag,newTag))
        {
            saveMp3AndBackup(mp3File,"bak");
        }
    }
}
