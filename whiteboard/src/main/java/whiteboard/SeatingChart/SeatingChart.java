package whiteboard.SeatingChart;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/*@Entity
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
		@Column(name = "X")
		public int x;
		@Column(name = "Y")
		public int y;
		
		public SeatingChart(int chartId, int lectureId, int studId, int x, int y) {
			super();
			this.chartId = chartId;
			this.lectureId = lectureId;
			this.studId = studId;
			this.x = x;
			this.y = y;
		}

		public SeatingChart() {
			
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
			return this.chartId+"===="+this.lectureId+"===="+this.studId+"===="+this.x+"===="+this.y;
		}
		
		public String parseStringData(String[] dataSplit) {
			this.chartId = 
			
			this.title = dataSplit[0];
			this.lecDate = Date.valueOf(dataSplit[1]);
			this.courseCode = dataSplit[2];
			this.details =dataSplit[3];
			this.link = dataSplit[4];
			this.profId = Integer.parseInt(dataSplit[5]);
			this.lectureId = Integer.parseInt(dataSplit[6]);
			if(dataSplit[8].contains("attendance")) {
				this.openAttendance = true;
			}else {
				this.openAttendance = false;
			}
			return "";
		}
	}
	*/
