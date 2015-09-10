/**
 * Copyright (C) 2015 android10.org Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.rx.sample.data;

import com.fernandocejas.android10.rx.sample.executor.JobExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DataManager {

  private final List<Integer> numbers;
  private final List<String> elements;
  private final RandomStringGenerator randomStringGenerator;
  private final Executor jobExecutor;

  private static final Func1<String, String> RANDOM_ITEM_FUNCTION = new Func1<String, String>() {
    @Override public String call(String string) {
      return "RandomItem" + string;
    }
  };

  public DataManager() {
    this.numbers = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10));
    this.elements = new ArrayList<>();
    this.randomStringGenerator = new RandomStringGenerator();

    populateUsernameList();
    jobExecutor = JobExecutor.getInstance();
  }

  public Observable<Integer> getNumbers() {
    return Observable.from(numbers);
  }

  public List<Integer> getNumbersSync() {
    return this.numbers;
  }

  public Observable<Integer> squareOfAsync(int number) {
    return Observable.just(number * number).subscribeOn(Schedulers.from(this.jobExecutor));
  }

  public Observable<String> getElements() {
    return Observable.from(elements);
  }

  public Observable<String> getNewElement() {
    return Observable.just(randomStringGenerator.nextString()).map(RANDOM_ITEM_FUNCTION);
  }

  private void populateUsernameList() {
    for (int i = 0; i < 10; i++) {
      this.elements.add(randomStringGenerator.nextString());
    }
  }
}
