// import { Routes } from '@angular/router';
// import { Home } from './home/home';
// import { Register } from './auth/register/register';
// import { Login } from './auth/login/login';
// import { ChangePassword } from './auth/change-password/change-password';
// import { UserDashboard } from './user/user-dashboard/user-dashboard';
// import { Myprofile } from './user/myprofile/myprofile';
// import { Joblistings } from './user/joblistings/joblistings';
// import { Myapplications } from './user/myapplications/myapplications';
// import { Noticeboard } from './user/noticeboard/noticeboard';
// import { Layout } from './layout/layout';
// import { Applymodal } from './user/applymodal/applymodal';
// import { Adminlogin } from './admin/adminlogin/adminlogin';
// import { Adminlayout } from './admin/adminlayout/adminlayout';
// import { Admindashboard } from './admin/admindashboard/admindashboard';
// import { Managejobs } from './admin/managejobs/managejobs';
// import { Students } from './admin/students/students';
// import { Allapplications } from './admin/allapplications/allapplications';
// import { Adminnoticeboard } from './admin/adminnoticeboard/adminnoticeboard';
// import { Allstudents } from './admin/allstudents/allstudents';
// import { roleGuard } from './auth/role.guard';
// export const routes: Routes = [

//   { path: '', component: Home },
//   { path: 'register', component: Register },
//   { path: 'login', component: Login },
//   { path: 'change-password', component: ChangePassword },
//   { path: 'applyModal', component: Applymodal, canActivate: [roleGuard]},
//   {path:'adminlogin',component:Adminlogin},
//   {path:'adminlayout',component:Adminlayout,
//     canActivate: [roleGuard],
//     children:[
//       {path:'admindashboard',
//         component:Admindashboard,
       
//       data: { roles: ['ADMIN'] }
//       },
//       {path:'managejobs',component:Managejobs},
//       {path:'students',component:Allstudents},
//       {path:'allapplication',component:Allapplications},
//       {path:'adminnoticeboard',component:Adminnoticeboard}
//     ]
//   },
//  // Layout is parent path and its child path Student
//   {
//     path: 'layout',
//     component: Layout,
//     canActivate: [roleGuard],
//     children: [
//       { path: 'userdashboard',
//         component: UserDashboard, 
//         canActivate: [roleGuard],
//         data: { roles: ['STUDENT'] }
//       },
//       { path: 'myprofile', component: Myprofile },
//       { path: 'jobs', component: Joblistings },
//       { path: 'applications', component: Myapplications },
//       { path: 'notice', component: Noticeboard },
//     ]
//   },
//   { path: '**', redirectTo: '' }

// ];



import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Register } from './auth/register/register';
import { Login } from './auth/login/login';
import { ChangePassword } from './auth/change-password/change-password';
import { UserDashboard } from './user/user-dashboard/user-dashboard';
import { Myprofile } from './user/myprofile/myprofile';
import { Joblistings } from './user/joblistings/joblistings';
import { Myapplications } from './user/myapplications/myapplications';
import { Noticeboard } from './user/noticeboard/noticeboard';
import { Layout } from './layout/layout';
import { Applymodal } from './user/applymodal/applymodal';
import { Adminlogin } from './admin/adminlogin/adminlogin';
import { Adminlayout } from './admin/adminlayout/adminlayout';
import { Admindashboard } from './admin/admindashboard/admindashboard';
import { Managejobs } from './admin/managejobs/managejobs';
import { Allapplications } from './admin/allapplications/allapplications';
import { Adminnoticeboard } from './admin/adminnoticeboard/adminnoticeboard';
import { Allstudents } from './admin/allstudents/allstudents';
import { roleGuard } from './auth/role.guard';

export const routes: Routes = [

  //  Public routes — no guard
  { path: '', component: Home },
  { path: 'register', component: Register },
  { path: 'login', component: Login },
  { path: 'adminlogin', component: Adminlogin },

  //  Protected misc routes
  { path: 'change-password', component: ChangePassword, canActivate: [roleGuard] },
  { path: 'applyModal', component: Applymodal, canActivate: [roleGuard] },

  //  Admin layout — protected, ADMIN role only
  {
    path: 'adminlayout',
    component: Adminlayout,
    canActivate: [roleGuard],
    data: { roles: ['ADMIN'] },
    children: [
      { path: 'admindashboard', component: Admindashboard },
      { path: 'managejobs', component: Managejobs },
      { path: 'students', component: Allstudents },
      { path: 'allapplication', component: Allapplications },
      { path: 'adminnoticeboard', component: Adminnoticeboard }
    ]
  },

  //  Student layout — protected, STUDENT role only
  {
    path: 'layout',
    component: Layout,
    canActivate: [roleGuard],
    data: { roles: ['STUDENT'] },
    children: [
      { path: 'userdashboard', component: UserDashboard },
      { path: 'myprofile', component: Myprofile },
      { path: 'jobs', component: Joblistings },
      { path: 'applications', component: Myapplications },
      { path: 'notice', component: Noticeboard },
    ]
  },

  { path: '**', redirectTo: '' }
];