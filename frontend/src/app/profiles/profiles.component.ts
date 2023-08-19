import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

export interface Profile {
  firstName: string;
  lastName: string;
  year: string;
}

const ELEMENT_DATA: Profile[] = [
  {firstName: 'Adrian', lastName: 'Valenciano Miguelez', year: '2004'},
  {firstName: 'Paula Sara', lastName: 'Bliznakoff Montero', year: '2005'},
  {firstName: 'David', lastName: 'Monje Mencía', year: '2006'},
  {firstName: 'Mario', lastName: 'De la Torre De Celis', year: '2006'},
  {firstName: 'Victor', lastName: 'Sacristán Gutierrez', year: '2006'},
  {firstName: 'Carla', lastName: 'Jimenez González', year: '2006'},
  {firstName: 'Irene Beatriz', lastName: 'Bliznakoff Montero', year: '2006'},
  {firstName: 'Paula', lastName: 'Padial Torres', year: '2007'},
  {firstName: 'Adrian', lastName: 'Martiño Ortega', year: '2008'},
  { firstName: 'Hugo', lastName: 'Valenciano Miguelez', year: '2008'},
  { firstName: 'Ivan', lastName: 'Andrés González', year: '2008'},
  { firstName: 'Paula', lastName: 'Álvarez Fernández-Cid', year: '2008'},
  { firstName: 'Sara', lastName: 'Andrés González', year: '2008'},
  { firstName: 'Sara', lastName: 'González Ordoñez', year: '2010'},
  { firstName: 'Unai', lastName: 'Martinez Siddi', year: '2009'},
  { firstName: 'Kevin', lastName: 'García Amez', year: '2010'},
  { firstName: 'Miguel', lastName: 'Alija Valenciano', year: '2010'},
  { firstName: 'Celia', lastName: 'Alija Valenciano', year: '2011'},
  { firstName: 'Alexia', lastName: 'García Álvarez', year: '2012'},
  { firstName: 'Nagore', lastName: 'Puente Oblanca', year: '2012'},
];

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent {

  displayedColumns: string[] = ['firstName', 'lastName', 'year'];
  dataSource = new MatTableDataSource<Profile>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {

  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
