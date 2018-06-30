package io.github.JangHJoon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.DefaultStyledDocument;


public class TA_To_TP {
	
	/**
	 * �гξ��� JTextArea�� JTextPane���� �����ϸ鼭 �׸�, ��Ÿ�� �߰�
	 * Ű���� ������ �ƹ����� ���� �ʴ´�.
	 * 
	 * @param container JTextArea�� �����ϴ� �г�
	 * @param category	�̹��� �߰��� �� �ʿ��� ī�װ�
	 */
	public static void changeStyleText(Container container, String category) {

		// �г��� �����ͼ� ���� ��� JTextArea�� ã�� �۾� 
		Component[] cps = container.getComponents();
		for (Component cp : cps) {
			if (cp.getClass().equals(JPanel.class)) {
				changeStyleText((JPanel) cp, category);
			}

			if (cp.getClass().equals(JTextArea.class)) {
				
				JTextArea src = (JTextArea) cp;
								
				// ������ JTextArea ������ ����
				String str = src.getText();			
				Dimension size = new Dimension(src.getSize().width,
						src.getSize().height);
				Font font = src.getFont();
				Border border = src.getBorder();
				JPanel parent = (JPanel)src.getParent();
				Color color = src.getForeground();
				
				
				// Ű���� ������ ����
				if(str.indexOf("```") == -1 && str.indexOf("![](") == -1){
					return;
				}				
				
				// �θ� �гο��� JTextArea�� ����
				parent.remove(src);
				parent.updateUI();
		
				// JTextPane�� �����Ͽ� ���� ��ġ�� ����
				DefaultStyledDocument document = new DefaultStyledDocument();
				JTextPane tp = new JTextPane(document);
				parent.add(tp);					
				
				// �޾ƿ� ��Ʈ���� LineWrap ó��
				str = wordWrap(str, 150);				
				
		
				
				// JTextPane ���� 
				tp.setBorder(border);
				tp.setSize(size);
				tp.setFont(font);
				tp.setEditable(false);
				tp.setForeground(color);

				// �г� ���� ó��
				if(parent.getLayout() instanceof FlowLayout){
					FlowLayout layout = (FlowLayout)parent.getLayout();
					layout.setAlignment(FlowLayout.LEFT);
				}
				parent.updateUI();				
			}
		}

	}
	
	/**
	 * Performs word wrapping.  Returns the input string with long lines of
	 * text cut (between words) for readability.
	 * 
	 * @param in     text to be word-wrapped
	 * @param length number of characters in a line
	 */
	public static String wordWrap(String in, int length) {
	    //:: Trim
	    while(in.length() > 0 && (in.charAt(0) == '\t' || in.charAt(0) == ' '))
	        in = in.substring(1);
	    
	    //:: If Small Enough Already, Return Original
	    if(in.length() < length)
	        return in;
	    
	    //:: If Next length Contains Newline, Split There
	    if(in.substring(0, length).contains("\n")){
	    	System.out.println(in.substring(0, in.indexOf("\n")).trim());
	        return in.substring(0, in.indexOf("\n")).trim() + "\n" + wordWrap(in.substring(in.indexOf("\n") + 1), length);
	    }
	    
	    //:: Otherwise, Split Along Nearest Previous Space/Tab/Dash
	    int spaceIndex = Math.max(Math.max( in.lastIndexOf(" ",  length),
	                                        in.lastIndexOf("\t", length)),
	                                        in.lastIndexOf("-",  length));
	    
	    //:: If No Nearest Space, Split At length
	    if(spaceIndex == -1)
	        spaceIndex = length;
	    
	    //:: Split
	    return in.substring(0, spaceIndex).trim() + "\n" + wordWrap(in.substring(spaceIndex), length);
	}
	//private static final String newline = System.getProperty("line.separator");

	
		

}
