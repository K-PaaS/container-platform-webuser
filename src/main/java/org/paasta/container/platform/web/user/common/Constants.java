package org.paasta.container.platform.web.user.common;

/**
 * Constants 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.25
 */
public class Constants {

    // COMMON
    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    public static final String RESULT_STATUS_FAIL = "FAIL";

    public static final String TOKEN_KEY = "cp_admin";

    public static final String TARGET_CP_API = "cpApi";
    public static final String TARGET_COMMON_API = "commonApi";

    public static final String DEFAULT_NAMESPACE_NAME = "temp-namespace";
    public static final String NOT_ASSIGNED_ROLE = "NOT_ASSIGNED_ROLE";
    public static final String DEFAULT_INIT_ROLE = "init-role";
    public static final String DEFAULT_CLUSTER_ADMIN_ROLE = "cluster-admin"; // k8s default cluster role's name
    public static final String NAMESPACE_ADMIN = "NAMESPACE_ADMIN";

    public static final String API_URL = "/api";
    public static final String CP_BASE_URL = "/container-platform";
    public static final String CP_INIT_URI = "/container-platform/intro/overview";

    public static final String URI_CLUSTER_NODES = "/container-platform/clusters/nodes";
    public static final String URI_CLUSTER_NAMESPACES = "/container-platform/clusters/namespaces";


    // DASHBOARD URI
    public static final String URI_LOGIN = "/signUp/login";
    public static final String URI_INTRO_OVERVIEW = "/container-platform/intro/overview";
    public static final String URI_INTRO_ACCESS_INFO = "/container-platform/intro/accessInfo";
    public static final String URI_INTRO_PRIVATE_REGISTRY_INFO = "/container-platform/intro/privateRegistryInfo";

    public static final String URI_WORKLOAD_OVERVIEW = "/container-platform/workloads/overview";
    public static final String URI_WORKLOAD_DEPLOYMENTS = "/container-platform/workloads/deployments";
    public static final String URI_WORKLOAD_PODS = "/container-platform/workloads/pods";
    public static final String URI_WORKLOAD_REPLICA_SETS = "/container-platform/workloads/replicaSets";

    public static final String URI_SERVICES = "/container-platform/services";

    public static final String URI_STORAGES = "/container-platform/storages";

    public static final String URI_USERS = "/container-platform/users";
    public static final String URI_USERS_CONFIG = "/container-platform/users/config";
    public static final String URI_USERS_INFO = "/container-platform/info";

    public static final String URI_ROLES = "/container-platform/roles";

    public static final String URI_API_CREATE_RESOURCE = "/clusters/cp-cluster/namespaces/{namespace:.+}/{resourceKind:.+}";

    // API URI
    public static final String URL_API_LOGIN = "/login?isAdmin=false";
    public static final String URI_API_NAME_SPACES_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}";
    public static final String URI_API_NAME_SPACES_RESOURCE_QUOTAS = "/namespaces/{namespace:.+}/resourceQuotas";

    public static final String URI_API_NODES_LIST = "/clusters/cp-cluster/nodes/{nodeName:.+}";

    public static final String URI_API_DEPLOYMENTS_LIST = "/clusters/cp-cluster/namespaces/{namespace:.+}/deployments";
    public static final String URI_API_DEPLOYMENTS_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/deployments/{deploymentName:.+}";
    public static final String URI_API_DEPLOYMENTS_YAML = "/clusters/cp-cluster/namespaces/{namespace:.+}/deployments/{deploymentName:.+}/yaml";
    public static final String URI_API_DEPLOYMENTS_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/deployments";
    public static final String URI_API_DEPLOYMENTS_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/deployments/{deploymentName:.+}";
    public static final String URI_API_DEPLOYMENTS_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/deployments/{deploymentName:.+}";

    public static final String URI_API_PODS_LIST = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods";
    public static final String URI_API_PODS_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods/{podName:.+}";
    public static final String URI_API_PODS_YAML = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods/{podName:.+}/yaml";
    public static final String URI_API_PODS_LIST_BY_SELECTOR = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods/resources/{selector:.+}";
    public static final String URI_API_PODS_LIST_BY_NODE = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods/nodes/{nodeName:.+}";
    public static final String URI_API_PODS_LIST_BY_SELECTOR_WITH_SERVICE = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods/service/{serviceName:.+}/{selector:.+}";
    public static final String URI_API_PODS_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods";
    public static final String URI_API_POD_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods/{podName:.+}";
    public static final String URI_API_POD_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/pods/{podName:.+}";

    public static final String URI_API_REPLICA_SETS_LIST = "/clusters/cp-cluster/namespaces/{namespace:.+}/replicaSets";
    public static final String URI_API_REPLICA_SETS_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}";
    public static final String URI_API_REPLICA_SETS_YAML = "/clusters/cp-cluster/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}/yaml";
    public static final String URI_API_REPLICA_SETS_RESOURCES = "/clusters/cp-cluster/namespaces/{namespace:.+}/replicaSets/resources/{selector:.+}";
    public static final String URI_API_REPLICA_SETS_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/replicaSets";
    public static final String URI_API_REPLICA_SETS_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}";
    public static final String URI_API_REPLICA_SETS_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}";

    public static final String URI_API_SERVICES_LIST =   "/clusters/cp-cluster/namespaces/{namespace:.+}/services";
    public static final String URI_API_SERVICES_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/services/{serviceName:.+}";
    public static final String URI_API_SERVICES_YAML =   "/clusters/cp-cluster/namespaces/{namespace:.+}/services/{serviceName:.+}/yaml";
    public static final String URI_API_SERVICES_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/services/{serviceName:.+}";
    public static final String URI_API_SERVICES_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/services";
    public static final String URI_API_SERVICES_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/services/{serviceName:.+}";

    public static final String URI_API_ROLES_LIST =   "/clusters/cp-cluster/namespaces/{namespace:.+}/roles";
    public static final String URI_API_ROLES_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/roles/{roleName:.+}";
    public static final String URI_API_ROLES_YAML =   "/clusters/cp-cluster/namespaces/{namespace:.+}/roles/{roleName:.+}/yaml";
    public static final String URI_API_ROLES_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/roles/{roleName:.+}";
    public static final String URI_API_ROLES_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/roles";
    public static final String URI_API_ROLES_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/roles/{roleName:.+}";

    public static final String URI_API_USERS_LIST = "/clusters/cp-cluster/users";
    public static final String URI_API_USERS_LIST_BY_NAMESPACE = "/clusters/cp-cluster/namespaces/{namespace:.+}/users";
    public static final String URI_API_USERS_NAMES_LIST_BY_NAMESPACE = "/clusters/cp-cluster/namespaces/{namespace:.+}/users/names";
    public static final String URI_API_USERS_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/users/{userId:.+}";
    public static final String URI_API_USERS_INFO = "/clusters/cp-cluster/users/{userId:.+}";
    public static final String URI_API_USERS_CONFIG = "/clusters/cp-cluster/namespaces/{namespace:.+}/users";

    public static final String URI_API_ENDPOINTS_DETAIL = "/namespaces/{namespace:.+}/endpoints/{serviceName:.+}";

    public static final String URI_API_EVENTS_LIST = "/namespaces/{namespace:.+}/events/resources/{resourceUid:.+}";
    public static final String URI_API_NAMESPACE_EVENTS_LIST    = "/namespaces/{namespace:.+}/events";

    public static final String URI_API_ROLE_BINDINGS_DETAIL = "/namespaces/{namespace:.+}/roleBindings/{roleBindingName:.+}";

    public static final String URI_API_SERVICE_ACCOUNT_DETAIL = "/namespaces/{namespace:.+}/serviceAccounts/{cpAccountName:.+}";

    public static final String URI_API_SECRETS_DETAIL = "/namespaces/{namespace:.+}/secrets/{accessTokenName:.+}";

    public static final String URI_API_PERSISTENT_VOLUME_CLAIMS_LIST = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims";
    public static final String URI_API_PERSISTENT_VOLUME_CLAIMS_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";
    public static final String URI_API_PERSISTENT_VOLUME_CLAIMS_YAML = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}/yaml";
    public static final String URI_API_PERSISTENT_VOLUME_CLAIMS_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";
    public static final String URI_API_PERSISTENT_VOLUME_CLAIMS_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims";
    public static final String URI_API_PERSISTENT_VOLUME_CLAIMS_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";

    public static final String URI_API_STORAGES_LIST = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims";
    public static final String URI_API_STORAGES_DETAIL = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";
    public static final String URI_API_STORAGES_YAML = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}/yaml";
    public static final String URI_API_STORAGES_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";
    public static final String URI_API_STORAGES_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims";
    public static final String URI_API_STORAGES_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";

    public static final String URI_API_USERS_NAME_LIST = "/users/names";


    public static final String URI_API_COMMON_RESOURCE_CREATE_VIEW= "/common/resource/{namespace:.+}/{resourceKind:.+}/create";
    public static final String URI_API_COMMON_RESOURCE_UPDATE_VIEW = "/common/resource/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}/update";

    //COMMON RESOURCE
    public static final String URI_API_COMMON_RESOURCE_YAML = "/clusters/cp-cluster/namespaces/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}/yaml";
    public static final String URI_API_COMMON_RESOURCE_CREATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/{resourceKind:.+}";
    public static final String URI_API_COMMON_RESOURCE_UPDATE = "/clusters/cp-cluster/namespaces/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}";
    public static final String URI_API_COMMON_RESOURCE_DELETE = "/clusters/cp-cluster/namespaces/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}";

    // COMMON API URI
    public static final String URI_COMMON_API_USERS_LIST = "/users/serviceInstanceId/{serviceInstanceId:.+}/organizationGuid/{organizationGuid:.+}";
    public static final String URI_COMMON_API_USERS_DETAIL = "/users/serviceInstanceId/{serviceInstanceId:.+}/organizationGuid/{organizationGuid:.+}/userId/{userId:.+}/";
    public static final String URI_COMMON_API_USERS_VALID_EXIST_NAMESPACE = "/users/userId/{userId}/namespace/{namespace}";

    public static final String URI_COMMON_API_ROLES_LIST = "/roles/{roleSetCode:.+}";

    public static final Integer OVERVIEW_LIMIT_COUNT = 5;
    public static final Integer DEFAULT_LIMIT_COUNT = 10;

    public static final String CP_REMEMBER_ME_KEY = "cp-rememberme";

    private Constants() {
        throw new IllegalStateException();
    }

}
