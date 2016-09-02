package pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class Questions implements Serializable{

    String content;
    int id;
    long pubTime;
    int typeid;
    String answer;
    int cataid;
    int page;
    int totalElements;
    int totalPages;
    int size;
    String options;
//    String title;
//    boolean checked;

    public Questions() {
    }

    public Questions(String content, long pubTime, int id,String options, int size, int totalPages, int cataid, int totalElements, int page, int typeid, String answer) {
        this.content = content;
        this.pubTime = pubTime;
        this.id = id;
        this.options = options;
//        this.checked = checked;
//        this.title = title;
        this.size = size;
        this.totalPages = totalPages;
        this.cataid = cataid;
        this.totalElements = totalElements;
        this.page = page;
        this.typeid = typeid;
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPubTime() {
        return pubTime;
    }

    public void setPubTime(long pubTime) {
        this.pubTime = pubTime;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCataid() {
        return cataid;
    }

    public void setCataid(int cataid) {
        this.cataid = cataid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOptions(){
        return options;
    }

    public void setOptions(String options){
        this.options = options;
    }

//    public String getTitle() {
//        return title;
//    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

//    public boolean isChecked() {
//        return checked;
//    }

//    public void setChecked(boolean checked) {
//        this.checked = checked;
//    }

    @Override
    public String toString() {
        return "Questions{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", pubTime=" + pubTime +
                ", typeid=" + typeid +
                ", answer='" + answer + '\'' +
                ", cataid=" + cataid +
                ", page=" + page +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", size=" + size +
                ", options='" + options + '\'' +
                '}';
    }
}
