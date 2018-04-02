package whiteboard.SeatingChart;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="SeatingChart")
public class SeatingChart {
	
	@NotNull
	@Column(name = "ChartId")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int chartId;
	
		@Column(name = "lectureId")
		public int lectureId;
		@Column(name = "StudId")
		public int studId;
		@Column(name = "StudName")
		public String studName;

		@Column(name = "X")
		public int x;
		@Column(name = "Y")
		public int y;
		
		public SeatingChart(int chartId, int lectureId, int studId, String studName, int x, int y) {
			super();
			this.chartId = chartId;
			this.lectureId = lectureId;
			this.studId = studId;
			this.studName = studName;
			this.x = x;
			this.y = y;
		}

		public SeatingChart() {
			
		}
		
		public String getStudName() {
			return studName;
		}

		public void setStudName(String studName) {
			this.studName = studName;
		}
		
		public void setChartId(int chartId) {
			this.chartId = chartId;
		}
		public int getChartId() {
			return chartId;
		}
		public void setLectureId(int lectureId) {
			this.lectureId = lectureId;
		}
		public int getLectureId() {
			return lectureId;
		}
		public int getStudId() {
			return studId;
		}

		public void setStudId(int studId) {
			this.studId = studId;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
		
		@Override
		public String toString() {
			return this.chartId+"===="+this.lectureId+"===="+this.studId+"===="+this.studName+"===="+this.x+"===="+this.y;
		}
		
		public String parseStringData(String data) {
			String[] dataSplit = data.split("====");
			this.x = Integer.parseInt(dataSplit[0].substring(0,1));
			this.y = Integer.parseInt(dataSplit[0].substring(2,3));
			this.chartId = Integer.parseInt(dataSplit[0].substring(4, dataSplit[0].length()));
			this.lectureId = Integer.parseInt(dataSplit[1]);
			this.studId = Integer.parseInt(dataSplit[2]);
			this.studName = dataSplit[3];
			return "";
		}
	}
	
