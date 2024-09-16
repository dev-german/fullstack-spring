const UserProfile = ({ name, age, gender, imageNumber }) => {
  gender = gender === "MALE" ? "men" : "women";
  return (
    <div>
      <p>{name}</p>
      <p>{age}</p>
      <img
        src={`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`}
      />
    </div>
  );
};

export default UserProfile;
