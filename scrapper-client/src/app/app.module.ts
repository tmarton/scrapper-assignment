import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule}   from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';


import { AppComponent } from './app.component';
import { ScrapperClientComponent } from './scrapper-client/scrapper-client.component';
import {MapToIterablePipe} from "./scrapper-client/map-to-iterable.pipe";

const appRoutes: Routes = [
  {
    path: '',
    component: ScrapperClientComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    MapToIterablePipe,
    ScrapperClientComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
