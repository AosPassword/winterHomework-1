package org.redrock.gayligayli.service.videoInfo.command;

import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.videoInfo.been.Barrage;

import java.util.ArrayList;

public class BarrageSocketCommand extends Command {
    private Receiver receiver;
    private long time;
    private int flag;
    private ArrayList<Barrage> barrageArraylist;
    private int top;

    public BarrageSocketCommand(Receiver receiver, int id) {
        this.receiver = receiver;
        barrageArraylist = VideoDao.getBarrageList(id);
        time = 0;
        flag = 0;
        top = barrageArraylist.size();
    }

    @Override
    public void exectue() {
    }

    public boolean next() throws InterruptedException {
        if (flag == top) {
            return false;
        }
        Barrage barrage = barrageArraylist.get(flag++);
        long newTime = Long.parseLong(barrage.getAppearTime()) * 1000;
        Thread.sleep( newTime- time);
        time=newTime;
        receiver.barrageSocket(barrage.toString());
        return true;
    }
}
