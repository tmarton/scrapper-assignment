import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-scrapper-client',
  templateUrl: './scrapper-client.component.html',
  styleUrls: ['./scrapper-client.component.css']
})
export class ScrapperClientComponent implements OnInit {

  constructor(private http: HttpClient) { }

  url: string;
  result: any = {};

  ngOnInit() {
  }

  hasResult(): boolean {
    return Object.keys(this.result).length !== 0;
  }

  keys() : Array<string> {
    return this.hasResult() ? Object.keys(this.result.wordFrequencies) : [];
  }

  fetchData() {
    this.http.get(`api/scrape?url=${this.url}`)
      .subscribe(res => {
        console.log(res);
        this.result = res;
      })
  }

}
