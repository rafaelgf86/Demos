/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.domain.Profile;
import mx.com.intx.domain.ProfileSection;
import mx.com.intx.entities.ProfileEntity;
import mx.com.intx.entities.TokenData;
import mx.com.intx.repository.IGenericDao;

/**
 * @author INTX
 *
 */
@Service
public class ProfileService extends Manager implements IProfileManager {

	private IGenericDao<Profile> profileDao;
	private IGenericDao<ProfileSection> profileSectionsDao;

	@Autowired
	public void setDaoProfile(IGenericDao<Profile> profileDao) {
		this.profileDao = profileDao;
		profileDao.setClazz(Profile.class);
	}

	@Autowired
	public void setProfileSectionsDao(IGenericDao<ProfileSection> profileSectionsDao) {
		this.profileSectionsDao = profileSectionsDao;
		profileSectionsDao.setClazz(ProfileSection.class);
	}

	@Override
	public List<ProfileEntity> getProfiles(TokenData tokenData) throws Exception {
		try {
			// Regresar resultados
			return convertToEntity(this.profileDao.findAll());
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.CONSULTA, "Get Profiles", getClass().getCanonicalName(), exception,
					tokenData);
			throw exception;
		}
	}

	@Override
	public ProfileEntity getProfile(long idProfile, TokenData tokenData) throws Exception {
		try {
			return convertToEntity(this.profileDao.getById(idProfile));
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.CONSULTA, "Get Profile", getClass().getCanonicalName(), exception,
					tokenData);
			throw exception;
		}
	}

	@Override
	public List<ProfileSection> getProfileSections(long idProfile) throws Exception {
		return profileSectionsDao.getByPropertyEQ("profileSectionId.profile.idProfile", idProfile);
	}

	public ProfileEntity convertToEntity(Profile profile) {
		ProfileEntity p = new ProfileEntity();
		p.setIdProfile(profile.getIdProfile());
		p.setDescription(profile.getDescription());
		p.setName(profile.getName());
		return p;
	}

	public List<ProfileEntity> convertToEntity(List<Profile> profiles) {
		List<ProfileEntity> p = new ArrayList<ProfileEntity>();
		for (Profile profile : profiles) {
			p.add(convertToEntity(profile));
		}
		return p;
	}
}
