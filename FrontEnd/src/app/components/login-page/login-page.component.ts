import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from "../../services/auth.service";
import {CustomerService} from "../../services/customer.service";
import {Customer} from "../../models/customer.model";


@Component({
  selector: 'app-login',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  loginForm!: FormGroup;
  customer!:Customer;
  username: string | undefined;

  constructor(private authService: AuthService,private customerService: CustomerService, private formBuilder: FormBuilder, private router: Router) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: [''],
      password: ['']
    });
    // @ts-ignore

  }

  get f() {
    return this.loginForm.controls;
  }

  login() {

    this.authService.login(
      {
        username: this.f['username'].value,
        password: this.f['password'].value
      }
    )
      .subscribe(success => {
        if (success) {
          if (localStorage.getItem("ROLES")!.includes("ADMIN")) {
            this.router.navigate(['/customers']);
            document.location.reload();
          } else {
            this.customerService.getIdCustomerByname(this.f['username'].value).subscribe({
              next: (data: any) => {
                this.customer =data;
              },
              error: (err: any) => {
                console.log(err);
              }
            });
            //this.router.navigate(['/customers']);
            this.router.navigate(['/customer-accounts/' +this.customer.id!]);
            setTimeout(function () {
              document.location.reload();
            }, 1000);
          }

        }
      });
  }

}
