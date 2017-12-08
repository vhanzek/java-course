<%@ page import="java.util.concurrent.TimeUnit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%! 
	private String getElapsedTime(long diff){
		StringBuilder time = new StringBuilder();
		
		long days = TimeUnit.MILLISECONDS.toDays(diff);
		diff -= TimeUnit.DAYS.toMillis(days);
		appendTime(days, "day", time);
		
		long hours = TimeUnit.MILLISECONDS.toHours(diff);
		TimeUnit.HOURS.toMillis(hours);
		appendTime(hours, "hour", time);
		
		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);;
		TimeUnit.MINUTES.toMillis(minutes);
		appendTime(minutes, "minute", time);

		long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
		diff -= TimeUnit.SECONDS.toMillis(seconds);
		appendTime(seconds, "second", time);

		long miliseconds = diff;
		appendTime(miliseconds, "milisecond", time);
		
		return time.toString();	
	}

	private void appendTime(long noTimeUnits, String timeUnit, StringBuilder sb){
		if(noTimeUnits > 0){
			sb.append(noTimeUnits + " " + timeUnit + 
					  (noTimeUnits>1 ? "s" : "") +  
					  (timeUnit.equals("milisecond") ? "." : " and "));
		}		  
	}
%>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Application Information</title>
	</head>
	<body bgcolor="<%= bgcolor %>">
		<%
			long start = (long) request.getServletContext().getAttribute("start");
			long diff = System.currentTimeMillis() - start;
			String elapsedTime = getElapsedTime(diff);
		 %>
		 Application has been running for <%= elapsedTime %>
	</body>
</html>