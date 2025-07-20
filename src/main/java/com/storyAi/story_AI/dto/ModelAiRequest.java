package com.storyAi.story_AI.dto;

import java.util.ArrayList;
import java.util.List;

public class ModelAiRequest {
List<String>prompts=new ArrayList<>();
String accent;
String output_type;
public List<String> getPrompts() {
	return prompts;
}
public void setPrompts(List<String> prompts) {
	this.prompts = prompts;
}
public String getAccent() {
	return accent;
}
public void setAccent(String accent) {
	this.accent = accent;
}
public String getOutput_type() {
	return output_type;
}
public void setOutput_type(String output_type) {
	this.output_type = output_type;
}
public ModelAiRequest() {
	super();
}
public ModelAiRequest(List<String> prompts, String accent, String output_type) {
	super();
	this.prompts = prompts;
	this.accent = accent;
	this.output_type = output_type;
}



}
