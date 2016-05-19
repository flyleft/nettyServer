package smart.entity;

import java.sql.Date;

/**
 * Created by jcala on 2016/5/9
 */
public class Te {
    /*
	DEFAULT_PARTICLE_SIZE	= 10
	DEFAULT_THRESHOLD_SIZE	= 10
	DEFAULT_MASHINE_LEARN	= false
	DEFAULT_MAX_MSG	    int			= 5
	CLIENT_VALID_SECOND		= 600
     */
    public static final int ONEDAY2SEC=86400;
    public static final int DEFAULT_THRESHOLD_SIZE=10;
    public static class IsOpenMashineLearn {
        private long Id;
        private int Open;
        private String Mac;
        private Date Time;

        public IsOpenMashineLearn(int open, String mac) {
            Open = open;
            Mac = mac;
            this.Time = new Date(System.currentTimeMillis());
        }

        public int getOpen() {
            return Open;
        }

        public Date getTime() {
            return Time;
        }

        public String getMac() {
            return Mac;
        }

        public IsOpenMashineLearn(int open) {
            Open = open;
        }
    }
    public static class ThresholdValue{
        private int Id;
        private long Threshold;
        private Date Date;

        public ThresholdValue(int id) {
            Id = id;
        }

        public void setThreshold(long threshold) {
            Threshold = threshold;
        }
    }
    /*
    type ParticleSize struct {
	Id          	int64			`orm:"unique;auto"`
	Particlesize	int				`orm:"default(1)"`
	Time            time.Time		`orm:"type(datetime);auto_now_add" `
}
     */
    public static class ParticleSize{
        private long id;
        private int particlesize;
        private Date date;

        public ParticleSize(int particlesize, long id) {
            this.particlesize = particlesize;
            this.id = id;
            this.date=new Date(System.currentTimeMillis());
        }
    }
    public static class TimeEvent{
        private long Id;
        private String Mac;
        private int EventTime;
        private int DeviceType;
        private int TimeScale;
        private int IRFRQ;
        private String OpType;
        private int Model;
        private int RespCode;
        private int Mask;
        private long Count;
        /*
        TimerEvent{Mac:status.Mac,EventTime:TI,DeviceType:status.DeviceType,
        TimeScale:INVALID,IRFRQ:INVALID,OpType:"",Model:SET_DEVICE_STATUS,
        RespCode:INVALID,Mask:status.Mask}
         */

        public String getMac() {
            return Mac;
        }

        public int getEventTime() {
            return EventTime;
        }

        public String getOpType() {
            return OpType;
        }

        public void setCount(long count) {
            Count = count;
        }

        public TimeEvent(String mac, int eventTime, int deviceType, int mask) {
            Mac = mac;
            EventTime = eventTime;
            DeviceType = deviceType;
            Mask = mask;
            this.TimeScale=Sub.INVALID;
            this.IRFRQ=Sub.INVALID;
            this.OpType="";
            this.Model=Sub.SET_DEVICE_STATUS;
            this.RespCode=Sub.INVALID;
        }
    }

}
