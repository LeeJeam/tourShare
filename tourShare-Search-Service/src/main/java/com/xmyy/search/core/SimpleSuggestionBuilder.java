package com.xmyy.search.core;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.suggest.Suggest.Suggestion;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleSuggestionBuilder {
	public static SimpleSuggestionBuilder from(String...indexs){
		return new SimpleSuggestionBuilder(indexs);
	}

	private SuggestionBuilder<?> suggestion;
	private List<String> indices;
	private String name;
	private String field;
	private String text;
	private Integer size;
	

	public <R> R get(ElasticsearchTemplate elasticsearchTemplate, Function<SearchResponse, R> mapper){
		Assert.notNull(this.suggestion, "suggestion");
		Assert.notEmpty(indices,"indices");
		Assert.notNull(name,"name");

		SuggestBuilder suggestBuilder = new SuggestBuilder();
		suggestBuilder.addSuggestion(name, suggestion);

		SearchResponse response = elasticsearchTemplate.suggest(suggestBuilder, this.indices.toArray(new String[0]));

		return mapper.apply(response);
	}
	
	public List<String> getTexts(ElasticsearchTemplate elasticsearchTemplate){
		return getOptions(elasticsearchTemplate).stream()
												.map(opt->opt.getText().string())
												.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public List<? extends Option> getOptions(ElasticsearchTemplate elasticsearchTemplate){
		if(StringUtils.isBlank(text)){
			return Collections.emptyList();
		}
		return get(elasticsearchTemplate, response->{
			Suggestion<?> suggestion = response.getSuggest().getSuggestion(name);
			if(suggestion==null || suggestion.getEntries().isEmpty()){
				return Collections.emptyList();
			}
			Suggestion.Entry<? extends Option> entry = suggestion.getEntries().get(0);
			List<? extends Option> options = entry.getOptions();
			return options;
		});
	}
	
	private SimpleSuggestionBuilder(String... indices) {
		super();
		this.indices = Lists.newArrayList(indices);
	}

	public SimpleSuggestionBuilder name(String name) {
		this.name = name;
		return this;
	}
	public SimpleSuggestionBuilder field(String field){
		this.field = field;
		return this;
	}
	public SimpleSuggestionBuilder text(String text){
		this.text = text;
		return this;
	}
	//term
	public SimpleSuggestionBuilder term(){
		return term(null);
	}
	public SimpleSuggestionBuilder term(Consumer<TermSuggestionBuilder> consumer){
		TermSuggestionBuilder term = SuggestBuilders.termSuggestion(field).text(text);
		build(term, consumer);
		return this;
	}
	
	//phrase
	public SimpleSuggestionBuilder phrase(){
		return phrase(null);
	}
	public SimpleSuggestionBuilder phrase(Consumer<PhraseSuggestionBuilder> consumer){
		PhraseSuggestionBuilder phrase = SuggestBuilders.phraseSuggestion(field).text(text);
		build(phrase, consumer);
		return this;
	}
	
	//completion
	public SimpleSuggestionBuilder completion(){
		return completion(null);
	}
	public SimpleSuggestionBuilder completion(Consumer<CompletionSuggestionBuilder> consumer){
		CompletionSuggestionBuilder completion = SuggestBuilders.completionSuggestion(field).text(text);
		build(completion, consumer);
		return this;
	}
	
	//completionFuzzy
	/*public SimpleSuggestionBuilder completionFuzzy(){
		return completionFuzzy(null);
	}
	public SimpleSuggestionBuilder completionFuzzy(Consumer<CompletionSuggestionBuilder> consumer){
		CompletionSuggestionBuilder completion = new CompletionSuggestionBuilder(field).text(text);

		build(completion, consumer);
		return this;
	}*/
	
	private <T extends SuggestionBuilder<?>> void build(T suggest, Consumer<T> consumer){
		if(size!=null){
			suggest.size(size);
		}
		if(consumer!=null){
			consumer.accept(suggest);
		}
		this.suggestion = suggest;
	}

	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	
	
}
