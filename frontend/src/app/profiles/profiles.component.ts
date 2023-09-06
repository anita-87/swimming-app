import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Profile} from "../models/profile";
import {ProfilesService} from "../services/profiles.service";

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent {

  displayedColumns: string[] = ['firstName', 'lastName', 'dateOfBirth'];
  profiles: Profile[];
  dataSource: MatTableDataSource<Profile>;
  numOfProfiles = 0
  isLoading = true;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private profilesService: ProfilesService) {
    this.dataSource = new MatTableDataSource<Profile>();
    this.profiles = [];
  }

  ngOnInit(): void {
    this.getSwimProfiles();
  }

  getSwimProfiles(): void {
    this.profilesService.getSwimmers()
      .subscribe(profiles => {
        this.profiles = profiles;
        this.dataSource =  new MatTableDataSource<Profile>(this.profiles.slice(0, 10));
        this.numOfProfiles = profiles.length;
        this.isLoading = false;
      })
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  handlePageEvent(e: PageEvent) {
    let startIndex = e.pageIndex * e.pageSize
    let endIndex = e.pageIndex * e.pageSize + e.pageSize
    this.dataSource = new MatTableDataSource<Profile>(this.profiles.slice(startIndex, endIndex));
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
