import { Routes } from '@angular/router';
import { Home } from './Components/home/home';
import { Signup } from './Components/signup/signup';
import { Login } from './Components/login/login';

export const routes: Routes = [

    {path:'',component:Home},
    {path:'signup',component:Signup},
    {path:'login',component:Login}
];
