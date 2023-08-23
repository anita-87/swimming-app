import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Profile} from "../models/profile";
import {ProfilesService} from "../services/profiles.service";

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent {

  displayedColumns: string[] = ['firstName', 'lastName', 'dateOfBirth'];
  dataSource: MatTableDataSource<Profile>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private profilesService: ProfilesService) {
    this.dataSource = new MatTableDataSource<Profile>();
  }

  ngOnInit(): void {
    this.getSwimProfiles();
  }

  getSwimProfiles(): void {
    this.profilesService.getSwimmers()
      .subscribe(profiles =>this.dataSource =  new MatTableDataSource<Profile>(profiles))
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
