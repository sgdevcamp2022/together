import AuthContext from '../../store/auth-context';
import ProfileForm from './ProfileForm';
import classes from './UserProfile.module.css';
import { useContext} from "react";

const UserProfile = () => {
  const authCtx = useContext(AuthContext);
  const userId = authCtx.userId;
  return (
    <section className={classes.profile}>
      <h1>Your Profile</h1>
      <ProfileForm userId={userId}/>
    </section>
  );
};

export default UserProfile;
