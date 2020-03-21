/*
 * Copyright 2015-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poc.reactivepoc.controller;

import com.poc.reactivepoc.entity.ReactivePost;
import com.poc.reactivepoc.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostRepository postRepository;

	@GetMapping
	public Flux<ReactivePost> all() {
		return this.postRepository.findAll();
	}

	@PostMapping
	public Mono<ReactivePost> create(@RequestBody ReactivePost reactivePost) {
		return this.postRepository.save(reactivePost);
	}

	@GetMapping("/{id}")
	public Mono<ReactivePost> get(@PathVariable("id") Integer id) {
		return this.postRepository.findById(id);
	}

	@PutMapping("/{id}")
	public Mono<ReactivePost> update(@PathVariable("id") Integer id, @RequestBody ReactivePost reactivePost) {
		return this.postRepository.findById(id).map(p -> {
			p.setTitle(reactivePost.getTitle());
			p.setContent(reactivePost.getContent());

			return p;
		}).flatMap(this.postRepository::save);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable("id") Integer id) {
		return this.postRepository.deleteById(id);
	}

}
