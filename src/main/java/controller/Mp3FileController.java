package controller;

import com.mpatric.mp3agic.*;
import model.ID3v2Tag;
import model.Reference;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Mp3FileController {
    private String workPath;

    public Mp3FileController(String workPath) {
        if (Files.exists(Paths.get(workPath))) {
            this.workPath = workPath + "/";
        } else {
            System.out.println("Caminho inexistente. (" + workPath + ")");
        }
    }

    public String getWorkPath() {
        String workPath = "Caminho não foi escolhido.";

        if (this.workPath != null) {
            workPath = this.workPath;
        }

        return workPath;
    }

    public Mp3File mp3FileFactory(String fileName) {
        Mp3File mp3file = null;

        if (this.workPath != null) {
            try {
                mp3file = new Mp3File(this.workPath + fileName);
            } catch (IOException ioe) {
                System.out.println("IOException(mp3FileFactory): " + ioe.getMessage());
            } catch (UnsupportedTagException ute) {
                System.out.println("UnsupportedTagException(mp3FileFactory): " + ute.getMessage());
            } catch (InvalidDataException ide) {
                System.out.println("InvalidDataException(mp3FileFactory): " + ide.getMessage());
            }
        }

        return mp3file;
    }

    public boolean fileExists(Mp3File mp3File) {
        boolean r = false;

        if (mp3File != null) {
            if (Files.exists(Paths.get(mp3File.getFilename()))) {
                r = true;
            } else {
                System.out.println("Arquivo inexistente. (" + mp3File.getFilename() + ")");
            }
        }

        return r;
    }

    // Atualiza ID3 de qualquer versão para ID3v23 (adiciona ID3v23 caso não tenha ID3)
    public boolean updateID3ToV23(Mp3File mp3File) {
        boolean r = false;

        if (fileExists(mp3File)) {
            if (!mp3File.hasId3v2Tag() && !mp3File.hasId3v1Tag()) {
                ID3v2 id3v23Tag = new ID3v23Tag();
                mp3File.setId3v2Tag(id3v23Tag);

                System.out.println("Mp3 não possui ID3. ID3v23 adicionado.");
                r = true;
            } else if (mp3File.hasId3v1Tag() && !mp3File.hasId3v2Tag()) {
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
                r = true;
            } else if (mp3File.hasId3v2Tag() && mp3File.getId3v2Tag().getObseleteFormat()) {
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
                setMp3Tag(mp3File, ID3v2Tag.TRACK, v2Track);
                setMp3Tag(mp3File, ID3v2Tag.ARTIST, v2Artist);
                setMp3Tag(mp3File, ID3v2Tag.TITLE, v2Title);
                setMp3Tag(mp3File, ID3v2Tag.ALBUM, v2Album);
                setMp3Tag(mp3File, ID3v2Tag.YEAR, v2Year);
                setMp3Tag(mp3File, ID3v2Tag.GENRE, String.valueOf(v2Genre));
                setMp3Tag(mp3File, ID3v2Tag.GENREDESCRIPTION, v2GenreDescription);
                setMp3Tag(mp3File, ID3v2Tag.COMMENT, v2Comment);
                setMp3Tag(mp3File, ID3v2Tag.COMPOSER, v2Composer);
                setMp3Tag(mp3File, ID3v2Tag.PUBLISHER, v2Publisher);
                setMp3Tag(mp3File, ID3v2Tag.ORIGINALARTIST, v2OriginalArtist);
                setMp3Tag(mp3File, ID3v2Tag.ALBUMARTIST, v2AlbumArtist);
                setMp3Tag(mp3File, ID3v2Tag.COPYRIGHT, v2Copyright);
                setMp3Tag(mp3File, ID3v2Tag.URL, v2Url);
                setMp3Tag(mp3File, ID3v2Tag.ENCODER, v2Encoder);

                System.out.println("Mp3 possui ID3v22. Atualizado para ID3v23.");
                r = true;
            }
        }

        return r;
    }

    // Retorna tag escolhida de um Mp3File (ID3v22, ID3v23 e ID3v24)
    public String getMp3Tag(Mp3File mp3File, ID3v2Tag tag) {
        String mp3Tag = null;

        if (fileExists(mp3File) && mp3File.hasId3v2Tag()) {
            ID3v2 mp3ID3v2Tag = mp3File.getId3v2Tag();
            switch (tag) {
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

    // Altera qualquer tag de um Mp3File (ID3v23 e ID3v24)
    public boolean setMp3Tag(Mp3File mp3File, ID3v2Tag id3v2Tag, String newTag) {
        boolean r = false;

        if (fileExists(mp3File) && mp3File.hasId3v2Tag() && newTag != null) {
            ID3v2 mp3ID3v2Tag = mp3File.getId3v2Tag();
            switch (id3v2Tag) {
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
                    try {
                        mp3ID3v2Tag.setGenreDescription(newTag);
                    } catch (IllegalArgumentException iae) {
                        System.out.println("IllegalArgumentException(updateID3ToV23): " + iae.getMessage());
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

            if (r) {
                r = false;
            } else {
                System.out.println(id3v2Tag.name() + " alterado. (" + mp3File.getFilename() + ")");
                r = true;
            }
        }

        return r;
    }

    // Altera uma tag através do nome do Mp3File
    public boolean setFilenameAsTag(Mp3File mp3file, Reference reference, ID3v2Tag id3v2Tag) {
        boolean r = false;

        if (fileExists(mp3file)) {
            File file = new File(mp3file.getFilename());
            String newTag;
            String fileName = file.getName().substring(0, file.getName().length() - 4);
            int beginSubstring = -1;
            int endSubstring = -1;

            if (!reference.isBefore()) {
                int j = 0;
                for (int i = 0; i < fileName.length(); i++) {
                    if (fileName.charAt(i) == reference.getCharacter()) {
                        j++;

                        if (j == reference.getPlace() - 1) {
                            beginSubstring = i + 1;
                        }
                        if (j == reference.getPlace()) {
                            endSubstring = i;
                            if (reference.getPlace() == 1) {
                                beginSubstring = 0;
                            }
                            break;
                        }
                    }
                }
                if (endSubstring == -1) {
                    //System.out.println("Begin index: " + beginSubstring);
                    newTag = fileName.substring(beginSubstring);
                } else {
                    //System.out.println("Begin index: " + beginSubstring);
                    //System.out.println("End index: " + endSubstring);
                    newTag = fileName.substring(beginSubstring, endSubstring);
                }
            } else {
                int j = 0;
                for (int i = 0; i < fileName.length(); i++) {
                    if (fileName.charAt(i) == reference.getCharacter()) {
                        j++;

                        if (j == reference.getPlace()) {
                            beginSubstring = i + 1;
                        }
                        if (j == reference.getPlace() + 1) {
                            endSubstring = i;
                            break;
                        }
                    }
                }
                if (endSubstring == -1) {
                    //System.out.println("Begin index: " + beginSubstring);
                    newTag = fileName.substring(beginSubstring);
                } else {
                    //System.out.println("Begin index: " + beginSubstring);
                    //System.out.println("End index: " + endSubstring);
                    newTag = fileName.substring(beginSubstring, endSubstring);
                }
            }

            System.out.println(id3v2Tag + " a ser inserido: " + newTag);
            if (this.setMp3Tag(mp3file, id3v2Tag, newTag)) {
                r = true;
            }
        }

        return r;
    }

    // Salva um Mp3File com um novo nome
    public boolean saveMp3(Mp3File mp3File, String newFileName) {
        boolean r = false;

        if (fileExists(mp3File)) {
            try {
                mp3File.save(this.workPath + newFileName);

                System.out.println("Novo mp3 salvo. (" + this.workPath + newFileName + ")");
                r = true;
            } catch (IOException ioe) {
                System.out.println("IOException(saveMp3): " + ioe.getMessage());
            } catch (NotSupportedException nse) {
                System.out.println("NotSupportedException(saveMp3): " + nse.getMessage());
            }
        }

        return r;
    }

    // Salva um Mp3File mantendo o nome
    public boolean saveMp3(Mp3File mp3File){
        boolean r = false;

        if (fileExists(mp3File)) {
            File file = new File(mp3File.getFilename());
            String backupFolder = "tempBak/";

            if (new File(this.workPath + backupFolder).mkdirs()) {
                System.out.println("Diretório criado (" + this.workPath + backupFolder + ")");
            }

            try {
                this.saveMp3(mp3File, file.getName().substring(0, file.getName().length() - 4) + "NEW.mp3");
                Files.move(Paths.get(mp3File.getFilename()), Paths.get(this.workPath + backupFolder + file.getName()), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Backup criado com sucesso. (" + this.workPath + backupFolder + file.getName() + ")");

                file = new File(mp3File.getFilename().substring(0, mp3File.getFilename().length() - 4) + "NEW.mp3");
                if (file.renameTo(new File(mp3File.getFilename()))) {
                    System.out.println("Arquivo renomeado. (" + mp3File.getFilename() + ")");

                    //Files.deleteIfExists(Paths.get(this.workPath + backupFolder));
                    FileUtils.deleteDirectory(new File(this.workPath + backupFolder));
                }

                r = true;
            } catch (IOException ioe) {
                System.out.println("IOException(saveMp3AndBackup): " + ioe.getMessage());
            }
        }

        return r;
    }

    // Salva um Mp3File e guarda o antigo em pasta backup
    public boolean saveMp3AndBackup(Mp3File mp3File, String backupFolder) {
        boolean r = false;

        if (fileExists(mp3File)) {
            File file = new File(mp3File.getFilename());
            backupFolder = backupFolder + "/";

            if (new File(this.workPath + backupFolder).mkdirs()) {
                System.out.println("Diretório criado (" + this.workPath + backupFolder + ")");
            }

            try {
                this.saveMp3(mp3File, file.getName().substring(0, file.getName().length() - 4) + "NEW.mp3");
                Files.move(Paths.get(mp3File.getFilename()), Paths.get(this.workPath + backupFolder + file.getName()), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Backup criado com sucesso. (" + this.workPath + backupFolder + file.getName() + ")");

                file = new File(mp3File.getFilename().substring(0, mp3File.getFilename().length() - 4) + "NEW.mp3");
                if (file.renameTo(new File(mp3File.getFilename()))) {
                    System.out.println("Arquivo renomeado. (" + mp3File.getFilename() + ")");
                }

                r = true;
            } catch (IOException ioe) {
                System.out.println("IOException(saveMp3AndBackup): " + ioe.getMessage());
            }
        }

        return r;
    }
}
