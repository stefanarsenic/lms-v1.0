import { Component } from '@angular/core';
import {Observable} from "rxjs";


@Component({
  selector: 'app-app-genericko',
  standalone: true,
  imports: [],
  templateUrl: './app-genericko.component.html',
  styleUrl: './app-genericko.component.css'
})
export class AppGenerickoComponent<T extends {id:null|number}> {
  items: T[] = [];
  headers: string[] = [];
  selectedItem: T | undefined;
  itemForEditing: T | undefined;

  tempItems:T[]=[]
  service2:any=null
  initialize(service: any) {
    this.update(service);
    this.service2=service
  }

  update(service: any) {
    (service.getAll() as Observable<T[]>).subscribe(data => {
      this.items = data;
      this.tempItems=[...data]
      this.headers = Object.keys(data[0]);
      this.selectedItem = data[0];
      console.log(data)
    });
  }

  setItemForEditing(id: any) {
    (this.service2.getAll() as Observable<T[]>).subscribe(data => {
      this.itemForEditing = data.find((item: any) => item.id === id);
    });
  }

  addItem(item: T) {
    if (this.itemForEditing === undefined) {
      (this.service2.create(item) as Observable<any>).subscribe(() => {
        this.update(this.service2);
      });
    } else {
      (this.service2.update(this.itemForEditing.id, item) as Observable<any>).subscribe(() => {
        this.update(this.service2);
      });
    }
  }

  deleteItem(id: number) {
    (this.service2.delete(id) as Observable<any>).subscribe(() => {
      this.update(this.service2);
    });
  }


}
