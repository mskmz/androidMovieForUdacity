package com.example.mskmz.androidmovieforudacity.model.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wangzekang on 2017/10/4.
 */

public class MoiveListVo implements Parcelable {
    private List<DateBean> results;

    MoiveListVo(Parcel in) {
        results=in.readArrayList(DateBean.class.getClassLoader());
    }


    public static final Creator<MoiveListVo> CREATOR = new Creator<MoiveListVo>() {
        @Override
        public MoiveListVo createFromParcel(Parcel in) {
            return new MoiveListVo(in);
        }

        @Override
        public MoiveListVo[] newArray(int size) {
            return new MoiveListVo[size];
        }
    };

    public MoiveListVo() {
    }

    public List<DateBean> getResults() {
        return results;
    }

    public void setResults(List<DateBean> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(results);
    }

    public static class DateBean implements Parcelable{
        private String vote_count;
        private String id;
        private String video;
        private String vote_average;
        private String title;
        private String popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private String backdrop_path;
        private String adult;
        private String overview;
        private String release_date;

        protected DateBean(Parcel in) {
            vote_count = in.readString();
            id = in.readString();
            video = in.readString();
            vote_average = in.readString();
            title = in.readString();
            popularity = in.readString();
            poster_path = in.readString();
            original_language = in.readString();
            original_title = in.readString();
            backdrop_path = in.readString();
            adult = in.readString();
            overview = in.readString();
            release_date = in.readString();
        }

        public static final Creator<DateBean> CREATOR = new Creator<DateBean>() {
            @Override
            public DateBean createFromParcel(Parcel in) {
                return new DateBean(in);
            }

            @Override
            public DateBean[] newArray(int size) {
                return new DateBean[size];
            }
        };

        public DateBean() {
        }

        public String getVote_count() {
            return vote_count;
        }

        public void setVote_count(String vote_count) {
            this.vote_count = vote_count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getVote_average() {
            return vote_average;
        }

        public void setVote_average(String vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPopularity() {
            return popularity;
        }

        public void setPopularity(String popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String getAdult() {
            return adult;
        }

        public void setAdult(String adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(vote_count);
            parcel.writeString(id);
            parcel.writeString(video);
            parcel.writeString(vote_average);
            parcel.writeString(title);
            parcel.writeString(popularity);
            parcel.writeString(poster_path);
            parcel.writeString(original_language);
            parcel.writeString(original_title);
            parcel.writeString(backdrop_path);
            parcel.writeString(adult);
            parcel.writeString(overview);
            parcel.writeString(release_date);
        }
    }
}
