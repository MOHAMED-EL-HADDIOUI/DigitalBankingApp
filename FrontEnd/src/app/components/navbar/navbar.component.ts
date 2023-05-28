import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {Customer} from "../../models/customer.model";
import {CustomerService} from "../../services/customer.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  roles!: string | null;
  customer!:Customer;

  constructor(public auth: AuthService,public customerService:CustomerService, public router: Router) {
  }

  ngOnInit(): void {
    this.roles = localStorage.getItem("ROLES");
    // @ts-ignore
    if(localStorage.getItem('username')!="admin")
    {
      // @ts-ignore
      this.customerService.getIdCustomerByname(localStorage.getItem('username')).subscribe({
        next: (data: any) => {
          this.customer =data;
        },
        error: (err: any) => {
          console.log(err);
        }
      });
    }

  }

  logout() {
    this.auth.logout();
    //document.location.reload();
    this.roles = null;
    this.router.navigateByUrl("/login");
  }
}
