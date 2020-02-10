package com.jdragon.tljrobot.client.window;

import com.jdragon.tljrobot.client.entry.TypingState;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;


public class CirecordFra extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTable table;
	public static DefaultTableModel tableM;
	JScrollPane tableN;
	public CirecordFra(){
		init();
		setVisible(true);
		setResizable(false);
		setTitle("跟打详情");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//设置关闭按钮
		setBounds(10,10,950,440);
		
	}
	void init(){
		setLayout(null);
		Object name[]={"","字词","上屏时间","临时速度","临时击键"},a[][] = null;
		tableM = new DefaultTableModel(a,name) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
		};
		table = new JTable(tableM);
		tableN = new JScrollPane(table);
		tableN.setBounds(0,0,300,400);
		add(tableN);
		int n=0;
		for(int i = 0;i<TypingState.typeWordsList.size();i++){
			TypingState.WordsState wordsState = TypingState.typeWordsList.get(i);
			Vector vRow1 = new Vector();
			vRow1.add(++n);
			vRow1.add(wordsState.getWords());
			vRow1.add(wordsState.getTime());
			vRow1.add(wordsState.getSpeed());
			vRow1.add(wordsState.getKeySpeed());
			tableM.addRow((vRow1));
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		JPanel jpanel = createDemoPanel(); 
		JScrollPane jpan1 = new JScrollPane(jpanel);
		jpanel.setLayout(null);
		jpanel.setBounds(300, 0, 600, 400);
//		jpan1.setBounds(345,0,800,600);
//		add(jpan1);
		add(jpanel);
	}
	private static CategoryDataset createDataset() {
		 DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		  //defaultcategorydataset.addValue()的参数解析：（数值，图例名，横坐标值）
		for(TypingState.WordsState wordsState:TypingState.typeWordsList){
			defaultcategorydataset.addValue(wordsState.getSpeed(),
					"速度",String.valueOf(wordsState.getTime()));
			defaultcategorydataset.addValue(wordsState.getKeySpeed()*20,
					"击键*20",String.valueOf(wordsState.getTime()));
			defaultcategorydataset.addValue(wordsState.getInstantaneousSpeed(),
					"瞬时速度",String.valueOf(wordsState.getTime()));
		}
		 return defaultcategorydataset; 
		 } 
	private static JFreeChart createChart(CategoryDataset categorydataset) {
		JFreeChart jfreechart = ChartFactory.createLineChart(
	    "",// 图表标题 
	    "time", // 主轴标签（x轴） 
	    "speed",// 范围轴标签（y轴） 
	    categorydataset, // 数据集 
	    PlotOrientation.VERTICAL,// 方向
	    true, // 是否包含图例 
	    true, // 提示信息是否显示 
	    false);// 是否使用urls 
		
		Font font = new Font("宋体",Font.BOLD,20);
		jfreechart.getTitle().setFont(font);
		
		jfreechart.getLegend().setItemFont(font);
//		jfreechart.getLegend().setMargin(0, 0, 0, 5);
		CategoryPlot plot1 = jfreechart.getCategoryPlot();// 获得图表区域对象??
		// 设置图表的纵轴和横轴?? 
		CategoryAxis domainAxis1 = plot1.getDomainAxis(); //获得横轴——目录轴
		ValueAxis rangeAxis1 = plot1.getRangeAxis(); //获得纵轴——数值轴
		domainAxis1.setLabelFont(font);
		domainAxis1.setTickLabelFont(font);
		
	  // 改变图表的背景颜色 
		jfreechart.setBackgroundPaint(Color.white); 
	    
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray); 
		categoryplot.setRangeGridlinePaint(Color.white); 
		categoryplot.setRangeGridlinesVisible(false); 
	  
		//修改范围轴。 我们将默认刻度值 （允许显示小数） 改成只显示整数的刻度值。 
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		
		//修改项目轴
		CategoryAxis rAxis =categoryplot.getDomainAxis();
		rAxis.setTickLabelsVisible(false);
		// 设置X轴上的Lable让其45度倾斜 
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 设置X轴上的Lable让其45度倾斜
		domainAxis.setLowerMargin(0.0); // 设置距离图片左端距离 
	  	domainAxis.setUpperMargin(0.0); // 设置距离图片右端距离 
	  	domainAxis.setTickMarksVisible(true);
	  	
	  	LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
	    .getRenderer(); 
	  	lineandshaperenderer.setShapesVisible(true); 
	  	lineandshaperenderer.setDrawOutlines(false); //加点
	  	lineandshaperenderer.setUseFillPaint(true); 
	  	lineandshaperenderer.setBaseFillPaint(Color.white); 
	  	lineandshaperenderer.setSeriesStroke(0, new BasicStroke(1.0F)); 
	  	lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F)); 
	  	lineandshaperenderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 
	    10.0, 10.0)); 
	  	lineandshaperenderer.setItemMargin(0.4); //设置x轴每个值的间距（不起作用？？） 
	    
	  	// 显示数据值 
	  	DecimalFormat decimalformat1 = new DecimalFormat("##.##");// 数据点显示数据值的格式 
	  	lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
	    "{2}", decimalformat1));// 设置数据项标签的生成器 
	  	lineandshaperenderer.setBaseItemLabelsVisible(false);// 基本项标签显示 
	  	lineandshaperenderer.setBaseShapesFilled(false);// 在数据点显示实心的小图标 
	  	return jfreechart; 
	 }
	 public static JPanel createDemoPanel() { 
	  JFreeChart jfreechart = createChart(createDataset());
	  try { 
	   ChartUtilities.saveChartAsJPEG(
	     new File("D:/LineChartDemo1.png"), //文件保存物理路径包括路径和文件名 
	     // 1.0f, //图片质量 ，0.0f~1.0f 
	      jfreechart, //图表对象 
	     1024, //图像宽度 ，这个将决定图表的横坐标值是否能完全显示还是显示省略号 
	     768); 
	  } catch (IOException e) { 
	   // TODO Auto-generated catch block 
	   e.printStackTrace(); 
	  } //图像高度 
	  return new ChartPanel(jfreechart);
	}
}
