package com.example.resturantdlv.Model;

public class Products {

        private String pname,date,time,category,image,pid,price;
        public Products()
        {

        }

        public Products(String pname, String date, String time, String category, String image, String pid,String price) {
            this.pname = pname;
            this.date = date;
            this.time = time;
            this.category = category;
            this.image = image;
            this.pid = pid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

}
