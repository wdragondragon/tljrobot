package com.jdragon.tljrobot.client;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Java 播放音频
 * @ClassName: MusicPlayer
 * @Description: TODO
 * @author: hyacinth
 * @date: 2019年10月25日 下午12:28:41
 * @Copyright: hyacinth
 */
public class test {

    public static void main(String[] args) throws Exception {
        test player=new test();
        //player.mp3_to_pcm("C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.mp3", "C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.pcm");
        //player.wav_to_pcm("C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.wav", "C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.pcm");

        player.get_info("C:\\Users\\10619\\Desktop\\mp3\\1.mp3");
        //player.get_info("C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.wav");

        //player.play_pcm("C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.pcm");
        //player.play_wav("C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.wav");
        player.play_mp3("C:\\Users\\10619\\Desktop\\mp3\\1.mp3");
        //player.play_flac("C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.flac");
    }

    /**
     * Java Music 播放 flac
     * @Title: play_flac
     * @Description: 播放 flac
     * @param path flac文件路径
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @date 2019年10月25日 下午12:28:41
     *
     */
    public void play_flac(String path) throws UnsupportedAudioFileException, IOException {
        File file=new File(path);
        if (!file.exists() || !path.toLowerCase().endsWith(".flac")) {
            return;
        }
        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audio.getFormat();
        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels()*2, format.getSampleRate(), false);
            audio = AudioSystem.getAudioInputStream(format, audio);
        }
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, AudioSystem.NOT_SPECIFIED);
        SourceDataLine data = null;
        try {
            data = (SourceDataLine) AudioSystem.getLine(info);
            data.open(format);
            data.start();
            byte[] bt = new byte[1024];
            while (audio.read(bt) != -1) {
                data.write(bt, 0, bt.length);
            }
            data.drain();
            data.stop();
            data.close();
            audio.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music 播放 wav
     * @Title: play_wav
     * @Description: 播放 wav
     * @param path wav 文件路径
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @date 2019年10月25日 下午12:28:41
     *
     */
    public void play_wav(String path) throws UnsupportedAudioFileException, IOException {
        File file=new File(path);
        if(!file.exists() || !path.toLowerCase().endsWith(".wav")) {
            throw new RuntimeException("文件不存在");
        }
        AudioInputStream stream=AudioSystem.getAudioInputStream(file);
        AudioFormat target = stream.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, target);
        SourceDataLine line = null;
        int len = -1;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(target);
            line.start();
            byte[] buffer = new byte[1024];
            while ((len = stream.read(buffer)) > 0) {
                line.write(buffer, 0, len);
            }
            line.drain();
            line.stop();
            line.close();
            stream.close();
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music 播放 pcm
     * @Title: play_pcm
     * @Description: 播放 pcm
     * @param path pcm文件路径
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @date 2019年10月25日 下午12:28:41
     *
     */
    public void play_pcm(String path) throws UnsupportedAudioFileException, IOException {
        File file=new File(path);
        if(!file.exists() || !path.toLowerCase().endsWith(".pcm")) {
            throw new RuntimeException("文件不存在");
        }
        AudioInputStream stream=AudioSystem.getAudioInputStream(file);
        AudioFormat target = stream.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, target, AudioSystem.NOT_SPECIFIED);
        SourceDataLine line = null;
        int len = -1;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(target);
            line.start();
            byte[] buffer = new byte[1024];
            while ((len = stream.read(buffer)) > 0) {
                line.write(buffer, 0, len);
            }
            line.drain();
            line.stop();
            line.close();
            stream.close();
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music 播放 mp3
     * @Title: play_mp3
     * @Description: 播放 mp3
     * @param path mp3文件路径
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @date 2019年10月25日 下午12:28:41
     *
     */
    public void play_mp3(String path) throws UnsupportedAudioFileException, IOException {
        File file=new File(path);
        if(!file.exists() || !path.toLowerCase().endsWith(".mp3")) {
            throw new RuntimeException("文件不存在");
        }
        AudioInputStream stream = null;
        //使用 mp3spi 解码 mp3 音频文件
        MpegAudioFileReader mp = new MpegAudioFileReader();
        stream = mp.getAudioInputStream(file);
        AudioFormat baseFormat = stream.getFormat();
        //设定输出格式为pcm格式的音频文件
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
        // 输出到音频
        stream = AudioSystem.getAudioInputStream(format, stream);
        AudioFormat target = stream.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, target, AudioSystem.NOT_SPECIFIED);
        SourceDataLine line = null;
        int len = -1;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(target);
            line.start();
            byte[] buffer = new byte[1024];
            while ((len = stream.read(buffer)) > 0) {
                line.write(buffer, 0, len);
            }
            line.drain();
            line.stop();
            line.close();
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            stream.close();
        }
    }

    /**
     * Java Music 读取pcm文件
     * @Title: read_pcm
     * @Description: 读取pcm文件
     * @param  path pcm文件路径
     * @return AudioInputStream
     * @date 2019年10月25日 下午12:28:41
     */
    public AudioInputStream read_pcm(String path) throws UnsupportedAudioFileException, IOException {
        File file=new File(path);
        if(!file.exists()) {
            return null;
        }
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        AudioFormat format=stream.getFormat();
        System.out.println(format.toString());
        return stream;
    }

    /**
     * Java Music 获取 mp3 脉冲编码调制
     * @Title: get_pcm_from_mp3
     * @Description: 获取 mp3 脉冲编码调制
     * @param  rpath mp3文件路径
     * @param  spath pcm文件保存路径
     * @return AudioInputStream
     * @date 2019年10月25日 下午12:28:41
     */
    public void get_pcm_from_mp3(String rpath,String spath) {
        File file=new File(rpath);
        if(!file.exists()) {
            return;
        }
        AudioInputStream stream = null;
        AudioFormat format = null;
        try {
            AudioInputStream in = null;
            //读取音频文件的类
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(file);
            AudioFormat baseFormat = in.getFormat();
            //设定输出格式为pcm格式的音频文件
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            //输出到音频
            stream = AudioSystem.getAudioInputStream(format, in);
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(spath));
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music mp3 转 pcm
     * @Title: mp3_to_pcm
     * @Description: MP3 PCM
     * @param  rpath MP3文件路径
     * @param  spath PCM文件保存路径
     * @return AudioInputStream
     * @date 2019年10月25日 下午12:28:41
     */
    public void mp3_to_pcm(String rpath,String spath) {
        File file=new File(rpath);
        if(!file.exists()) {
            return;
        }
        AudioInputStream stream = null;
        AudioFormat format = null;
        try {
            AudioInputStream in = null;
            //读取音频文件的类
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(file);
            AudioFormat baseFormat = in.getFormat();
            //设定输出格式为pcm格式的音频文件
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            //输出到音频
            stream = AudioSystem.getAudioInputStream(format, in);
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(spath));
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music wav转pcm
     * @Title: wav_to_pcm
     * @Description: wav转pcm
     * @param  wpath wav文件路径
     * @param  ppath pcm文件保存路径
     * @return AudioInputStream
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @date 2019年10月25日 下午12:28:41
     */
    public void wav_to_pcm(String wpath,String ppath) {
        File file=new File(wpath);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        AudioInputStream stream1;
        try {
            stream1 = AudioSystem.getAudioInputStream(file);
            // 根据实际情况修改 AudioFormat.Encoding.PCM_SIGNED
            AudioInputStream stream2=AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED,stream1);
            AudioSystem.write(stream2,AudioFileFormat.Type.WAVE,new File(ppath));
            stream2.close();
            stream1.close();
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music PCM转WAV
     * @Title: pcm_to_wav
     * @Description: PCM转WAV
     * @param  wpath WAV文件路径
     * @param  ppath PCM文件保存路径
     * @return AudioInputStream
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @date 2019年10月25日 下午12:28:41
     */
    public void pcm_to_wav(String ppath,String wpath) {

    }

    /**
     * Java Music 获取wav或者pcm文件的编码信息
     * @Title: get_info
     * @Description: 获取wav或者pcm文件的编码信息
     * @param path wav或者pcm文件路径
     * @return wav或者pcm文件的编码信息
     * @date 2019年10月25日 下午12:28:41
     */
    public String get_info(String path) {
        File file=new File(path);
        AudioInputStream ais;
        String result="";
        try {
            ais = AudioSystem.getAudioInputStream(file);
            AudioFormat af = ais.getFormat();
            result = af.toString();
            System.out.println(result);
            System.out.println("音频总帧数："+ais.getFrameLength());
            System.out.println("每秒播放帧数："+af.getSampleRate());
            float len = ais.getFrameLength()/af.getSampleRate();
            System.out.println("音频时长（秒）："+len);
            System.out.println("音频时长："+(int)len/60+"分"+len%60+"秒");
        } catch(UnsupportedAudioFileException e) {
            throw new RuntimeException(e.getMessage());
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

//    /**
//     * Java Music 获取mp3文件的图片
//     * @Title: get_image_from_mp3
//     * @Description: 获取mp3文件的图片
//     * @param mpath mp3flac文件路径
//     * @date 2019年10月25日 下午12:28:41
//     *
//     */
//    public void get_image_from_mp3(String mpath) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
//        File sourceFile = new File(mpath);
//        MP3File mp3file = new MP3File(sourceFile);
//        AbstractID3v2Tag tag = mp3file.getID3v2Tag();
//        AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
//        FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
//        byte[] image = body.getImageData();
//        Image img=Toolkit.getDefaultToolkit().createImage(image, 0,image.length);
//        ImageIcon icon = new ImageIcon(img);
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\梦涵 - 加减乘除.jpg");
//        fos.write(image);
//        fos.close();
//        System.out.println("width:"+icon.getIconWidth());
//        System.out.println("height:"+icon.getIconHeight());
//    }

}