package com.example.yin.service.impl;

import com.example.yin.common.R;
import com.example.yin.mapper.SongMapper;
import com.example.yin.model.domain.Song;
import com.example.yin.model.request.SongRequest;
import com.example.yin.service.SongService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Override
    public R allSong() {
        return R.success(null, songMapper.allSong());
    }

    @Override
    public R addSong(SongRequest addSongRequest,  MultipartFile mpfile) {
        Song song = new Song();
        BeanUtils.copyProperties(addSongRequest,song);
        String pic = "/img/songPic/tubiao.jpg";
        String fileName = mpfile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                return R.fatal("创建文件失败");
            }
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            mpfile.transferTo(dest);
        } catch (IOException e) {
            return R.fatal("上传失败" + e.getMessage());
        }
        song.setCreateTime(new Date());
        song.setUpdateTime(new Date());
        song.setPic(pic);
        song.setUrl(storeUrlPath);
        if (songMapper.insertSelective(song) > 0) {
            return R.success("上传成功", storeUrlPath);
        } else {
            return R.error("上传失败");
        }
    }

    @Override
    public R updateSongMsg(SongRequest updateSongRequest) {
        Song song = new Song();
        BeanUtils.copyProperties(updateSongRequest,song);
        song.setUpdateTime(new Date());
        if (songMapper.updateSongMsg(song) > 0) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    @Override
    public R updateSongUrl(MultipartFile urlFile, int id) {
        String fileName = urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                return R.fatal("创建目的文件夹失败");
            }
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            urlFile.transferTo(dest);
        } catch (IOException e) {
            return R.fatal("更新失败" + e.getMessage());
        }
        Song song = new Song();
        song.setId(id);
        song.setUrl(storeUrlPath);
        if (songMapper.updateSongUrl(song) > 0) {
            return R.success("更新成功", storeUrlPath);
        } else {
            return R.error("更新失败");
        }
    }

    @Override
    public R updateSongPic(MultipartFile urlFile, int id) {
        String fileName = System.currentTimeMillis() + urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                return R.fatal("创建文件夹失败");
            }
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/img/songPic/" + fileName;
        try {
            urlFile.transferTo(dest);
        } catch (IOException e) {
            return R.fatal("上传失败" + e.getMessage());
        }
        Song song = new Song();
        song.setId(id);
        song.setPic(storeUrlPath);
        if (songMapper.updateSongPic(song) > 0) {
            return R.success("上传成功", storeUrlPath);
        } else {
            return R.error("上传失败");
        }
    }

    @Override
    public R deleteSong(Integer id) {
        if (songMapper.deleteSong(id) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R songOfSingerId(Integer singerId) {
        return R.success(null, songMapper.songOfSingerId(singerId));
    }

    @Override
    public R songOfId(Integer id) {
        return R.success(null, songMapper.songOfId(id));
    }

    @Override
    public R songOfSingerName(String name) {
        return R.success(null, songMapper.songOfSingerName(name));
    }
}
