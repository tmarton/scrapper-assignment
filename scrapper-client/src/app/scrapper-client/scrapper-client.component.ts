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
  loading: boolean = false;
  errorMessage: string = '';

  ngOnInit() {
  }

  hasResult(): boolean {
    return Object.keys(this.result).length !== 0;
  }

  hasError(): boolean {
    return this.errorMessage ? true : false;
  }

  fetchData() {
    this.loading = true;
    this.errorMessage = '';
    this.http.get(`api/scrape?url=${this.url}`)
      .subscribe(res => {
        this.result = res;
        this.loading = false;
      }, err => {
        console.log(err);
        this.result = {};
        this.errorMessage = err.error.message;
        this.loading = false;
      });

  }

}
