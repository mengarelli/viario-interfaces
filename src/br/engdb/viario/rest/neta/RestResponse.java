package br.engdb.viario.rest.neta;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RestResponse<T> {
	public static final String OPERATION_UPDATE = "Update";
	public static final String OPERATION_DELETE = "Delete";
	public static final String TYPE_ERROR = "E";
	public static final String TYPE_WARNING = "W";
	public static final String TYPE_SUCCESS = "I";
	private String messageId;
	private String messageType;
	private String message;
	private Date transmissionDate;
	private int messageCode;
	private List<T> contents;
	private boolean hasMore = false;
	public RestResponse() {
		super();
		setContents(new LinkedList<>());
		setTransmissionDate(Calendar.getInstance().getTime());
	}
	public RestResponse(String messageId, String messageType, String message, int messageCode) {
		super();
		this.messageId = messageId;
		this.messageType = messageType;
		this.message = message;
		this.messageCode = messageCode;
		setContents(new LinkedList<>());
		setTransmissionDate(Calendar.getInstance().getTime());
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTransmissionDate() {
		return transmissionDate;
	}
	public void setTransmissionDate(Date transmissionDate) {
		this.transmissionDate = transmissionDate;
	}
	public int getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}
	public List<T> getContents() {
		return contents;
	}
	public void setContents(List<T> contents) {
		this.contents = contents;
	}
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
}
